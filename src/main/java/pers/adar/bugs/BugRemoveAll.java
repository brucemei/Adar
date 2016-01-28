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
package pers.adar.bugs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Bugs
 * 
 * @see pers.adar.bugs.BugRemoveAll#removeAll(Collection, Collection)
 * 
 * @see java.util.AbstractCollection#removeAll(Collection)
 */
public class BugRemoveAll {

	public static void main(String[] args) throws Exception {
		Collection<Bean> srcColl = srcColl(ArrayList.class);
		Collection<Bean> delColl = delColl(HashSet.class);

		System.out.println("RemoveAll(JDK) 删除前:" + srcColl.size());
		removeAll(srcColl, delColl);
		System.out.println("RemoveAll(JDK) 删除后:" + srcColl.size());

		Collection<Bean> srcColl2 = srcColl(ArrayList.class);
		Collection<Bean> delColl2 = delColl(HashSet.class);

		System.out.println("RemoveAll(Adar) 删除前:" + srcColl2.size());
		removeAllByAdar(srcColl2, delColl2);
		System.out.println("RemoveAll(Adar) 删除后:" + srcColl2.size());
	}

	/**
	 * JDK实现
	 * 
	 * @see java.util.AbstractCollection#removeAll(Collection)
	 */
	public static boolean removeAll(Collection<?> c1, Collection<?> c2) {
		boolean modified = false;
		Iterator<?> it = c1.iterator();
		while (it.hasNext()) {
			if (c2.contains(it.next())) { // 此处建议调用了c1的contains方法, 若c1为List, c2为Set,调用contains对象的不同将导致结果将不一致
										  // 调用作为this对象c1的contains方法更符合预期
				it.remove();
				modified = true;
			}
		}
		return modified;
	}

	/**
	 * Adar修改后实现
	 */
	public static boolean removeAllByAdar(Collection<?> c1, Collection<?> c2) {
		boolean modified = false;
		Iterator<?> it = c2.iterator();
		while (it.hasNext()) {
			Object next = it.next();
			if (c1.contains(next)) {
				c1.remove(next);
				modified = true;
			}
		}
		return modified;
	}

	@SuppressWarnings("rawtypes")
	private static Collection<Bean> srcColl(Class<? extends Collection> collType) throws Exception {
		Collection<Bean> collection = buildColl(collType);

		collection.add(new Bean("1"));
		collection.add(new Bean("2"));
		collection.add(new Bean("3"));

		return collection;
	}

	@SuppressWarnings("rawtypes")
	private static Collection<Bean> delColl(Class<? extends Collection> collType) throws Exception {
		Collection<Bean> collection = buildColl(collType);

		collection.add(new Bean("1"));

		return collection;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Collection<Bean> buildColl(Class<? extends Collection> collType) throws Exception {
		return collType.newInstance();
	}

	static class Bean {

		private String id;

		private String name;

		private String value;

		public Bean(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object bean) {
			return bean instanceof Bean ? id.equals(((Bean) bean).id) : false;
		}
	}
}
