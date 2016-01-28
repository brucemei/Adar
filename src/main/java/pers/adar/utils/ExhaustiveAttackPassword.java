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
package pers.adar.utils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 密码穷举器
 * 		For: 分布式暴力破解做准备
 */
public class ExhaustiveAttackPassword {
	
	public static void main(String[] args) {
		Exhaustives e1 = new Exhaustives();
		Exhaustives e2 = new Exhaustives();
		Exhaustives e3 = new Exhaustives();
		Exhaustives e4 = new Exhaustives();
		
		while (e1.hasNext()) {
			String c1 = e1.next();
			while (e2.hasNext()) {
				String c2 = e2.next();
				while (e3.hasNext()) {
					String c3 = e3.next();
					while (e4.hasNext()) {
						System.out.println(c1 + c2 + c3 + e4.next());
					}
					e4.reset();
				}
				e3.reset();
			}
			e2.reset();
		}
	}
	
	static class Exhaustives {
		private static final String[] CHARS;

		private static final String[] NUMS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

		private static final String[] LOW_ABCS = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

		private static final String[] UPP_ABCS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

		private static final String[] OTHERS = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "=", "_", "+", ".", ",", ":", "/", "\\", "|"};
		
		private int index;
		
		static {
			Set<String> charset = new LinkedHashSet<String>();
			addArrToCollection(charset, NUMS);
			addArrToCollection(charset, LOW_ABCS);
			addArrToCollection(charset, UPP_ABCS);
			addArrToCollection(charset, OTHERS);
			
			CHARS = charset.toArray(new String[charset.size()]);
		}
		
		private static <T> void addArrToCollection(Collection<T> collection, T[] arr) {
			for (T element : arr) {
				collection.add(element);
			}
		}
		
		public static int charCount() {
			return CHARS.length;
		}
		
		public boolean hasNext() {
			return index < CHARS.length;
		}
		
		public String next() {
			return CHARS[index++];
		}
		
		public void reset() {
			index = 0;
		}
	}
}
