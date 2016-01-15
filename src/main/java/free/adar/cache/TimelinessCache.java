/**
 * Copyright (c) 2015, adar.w (adar.w@outlook.com) 
 * 
 * http://www.adar-w.me
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package free.adar.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 时效性缓存实现
 */
public class TimelinessCache {

	private ConcurrentMap<String, Object> cache = new ConcurrentHashMap<String, Object>();

	private DelayQueue<DelayItem<Pair>> delayItems = new DelayQueue<DelayItem<Pair>>();
	
	public TimelinessCache() {
		Executors.newSingleThreadExecutor().submit(new Runnable() {
			
			@Override
			public void run() {
				for (;;) {
		            try {
		                DelayItem<Pair> delayItem = delayItems.take();
		                if (delayItem != null) {
		                    Pair pair = delayItem.getItem();
		                    cache.remove(pair.key, pair.value);
		                }
		            } catch (InterruptedException e) {
		                break;
		            }
		        }
			}
		});
	}

	public void put(String key, Object value, long time, TimeUnit unit) {
		Object oldValue = cache.put(key, value);
		if (oldValue != null)
			delayItems.remove(key);

		long nanoTime = TimeUnit.NANOSECONDS.convert(time, unit);
		delayItems.put(new DelayItem<Pair>(new Pair(key, value), nanoTime));
	}

	public Object get(String key) {
		return cache.get(key);
	}

	class Pair {
		public String key;

		public Object value;

		public Pair() {
		}

		public Pair(String key, Object value) {
			this.key = key;
			this.value = value;
		}
	}

	class DelayItem<T> implements Delayed {

		private final long NANO_ORIGIN = System.nanoTime();

		final long now() {
			return System.nanoTime() - NANO_ORIGIN;
		}

		private final AtomicLong sequencer = new AtomicLong(0);

		private final long sequenceNumber;

		private final long time;

		private final T item;

		public DelayItem(T submit, long timeout) {
			this.time = now() + timeout;
			this.item = submit;
			this.sequenceNumber = sequencer.getAndIncrement();
		}

		public T getItem() {
			return item;
		}

		public long getDelay(TimeUnit unit) {
			return unit.convert(time - now(), TimeUnit.NANOSECONDS);
		}

		public int compareTo(Delayed other) {
			if (other == this) {
				return 0;
			}
			
			if (other instanceof DelayItem) {
				DelayItem<?> x = (DelayItem<?>) other;
				long diff = time - x.time;
				if (diff < 0)
					return -1;
				else if (diff > 0)
					return 1;
				else if (sequenceNumber < x.sequenceNumber)
					return -1;
				else
					return 1;
			}
			
			long d = (getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS));
			return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
		}
	}
}
