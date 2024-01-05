package com.yw.course.advance.class08;

import java.util.HashMap;

/**
 * 该程序完全正确
 * @author yangwei
 */
public class Code02_TrieTree {

	/**
	 * 定义前缀树节点数据结构
	 */
	public static class Node {
		int p;
		int e;
		Node[] nexts;
		public Node() {
			this.p = 0;
			this.e = 0;
			// 0    a
			// 1    b
			// 2    c
			// ..   ..
			// 25   z
			// nexts[i] == null   i方向的路不存在
			// nexts[i] != null   i方向的路存在
			nexts = new Node[26];
		}
	}
	/**
	 * 实现前缀树
	 */
	public static class Trie {
		private final Node root;
		public Trie() {
			root = new Node();
		}
		// 添加单词
		public void insert(String word) {
			if (word == null || word.length() == 0) return;
			Node node = root;	// 拿到根节点
			node.p++;
			for (char c : word.toCharArray()) {	// 依次遍历字符
				int path = c - 'a'; 	// 字符统一减'a'，对应成走向哪条路
				// 如果这条路不存在，则创建之
				if (node.nexts[path] == null) node.nexts[path] = new Node();
				// 然后node往下走
				node = node.nexts[path];
				// 经过次数 p++
				node.p++;
			}
			// 最后结尾 e++
			node.e++;
		}
		// 删除单词
		public void delete(String word) {
			// 先查一下，如果不存在，则直接返回
			if (search(word) == 0) return;
			Node node = root;
			for (char c : word.toCharArray()) {
				int path = c - 'a';
				if (--node.nexts[path].p == 0) {
					// 若进过次数p减到0了，则将这条路整个删掉，防止内存泄漏
					node.nexts[path] = null;
					return;
				}
				// 否则继续往下找
				node = node.nexts[path];
			}
			node.e--;
		}
		// 查找单词出现次数
		public int search(String word) {
			if (word == null || word.length() == 0) return 0;
			Node node = root;
			for (char c : word.toCharArray()) {
				int path = c - 'a';
				// 如果这条路不存在，说明查找单词不存在，直接返回0
				if (node.nexts[path] == null) return 0;
				// 否则存在，继续往下找
				node = node.nexts[path];
			}
			// 循环退出，说明找到了，e值就是单词出现的次数
			return node.e;
		}
		// 查找某个前缀的单词出现次数
		public int searchPrefix(String prefix) {
			if (prefix == null || prefix.length() == 0) return 0;
			Node node = root;
			for (char c : prefix.toCharArray()) {
				int path = c - 'a';
				// 如果这条路不存在，说明查找单词前缀不存在，直接返回0
				if (node.nexts[path] == null) return 0;
				// 否则存在，继续往下找
				node = node.nexts[path];
			}
			// 循环退出，说明找到了，p值就是单词前缀出现的次数
			return node.p;
		}
	}

	public static class Node1 {
		public int pass;
		public int end;
		public Node1[] nexts;

		// char tmp = 'b'  (tmp - 'a')
		public Node1() {
			pass = 0;
			end = 0;
			// 0    a
			// 1    b
			// 2    c
			// ..   ..
			// 25   z
			// nexts[i] == null   i方向的路不存在
			// nexts[i] != null   i方向的路存在
			nexts = new Node1[26];
		}
	}

	public static class Trie1 {
		private Node1 root;

		public Trie1() {
			root = new Node1();
		}

		public void insert(String word) {
			if (word == null) {
				return;
			}
			char[] str = word.toCharArray();
			Node1 node = root;
			node.pass++;
			int path = 0;
			for (int i = 0; i < str.length; i++) { // 从左往右遍历字符
				path = str[i] - 'a'; // 由字符，对应成走向哪条路
				if (node.nexts[path] == null) {
					node.nexts[path] = new Node1();
				}
				node = node.nexts[path];
				node.pass++;
			}
			node.end++;
		}

		public void delete(String word) {
			if (search(word) != 0) {
				char[] chs = word.toCharArray();
				Node1 node = root;
				node.pass--;
				int path = 0;
				for (int i = 0; i < chs.length; i++) {
					path = chs[i] - 'a';
					if (--node.nexts[path].pass == 0) {
						node.nexts[path] = null;
						return;
					}
					node = node.nexts[path];
				}
				node.end--;
			}
		}

		// word这个单词之前加入过几次
		public int search(String word) {
			if (word == null) {
				return 0;
			}
			char[] chs = word.toCharArray();
			Node1 node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					return 0;
				}
				node = node.nexts[index];
			}
			return node.end;
		}

		// 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
		public int prefixNumber(String pre) {
			if (pre == null) {
				return 0;
			}
			char[] chs = pre.toCharArray();
			Node1 node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					return 0;
				}
				node = node.nexts[index];
			}
			return node.pass;
		}
	}

	public static class Node2 {
		public int pass;
		public int end;
		public HashMap<Integer, Node2> nexts;

		public Node2() {
			pass = 0;
			end = 0;
			nexts = new HashMap<>();
		}
	}

	public static class Trie2 {
		private Node2 root;

		public Trie2() {
			root = new Node2();
		}

		public void insert(String word) {
			if (word == null) {
				return;
			}
			char[] chs = word.toCharArray();
			Node2 node = root;
			node.pass++;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = (int) chs[i];
				if (!node.nexts.containsKey(index)) {
					node.nexts.put(index, new Node2());
				}
				node = node.nexts.get(index);
				node.pass++;
			}
			node.end++;
		}

		public void delete(String word) {
			if (search(word) != 0) {
				char[] chs = word.toCharArray();
				Node2 node = root;
				node.pass--;
				int index = 0;
				for (int i = 0; i < chs.length; i++) {
					index = (int) chs[i];
					if (--node.nexts.get(index).pass == 0) {
						node.nexts.remove(index);
						return;
					}
					node = node.nexts.get(index);
				}
				node.end--;
			}
		}

		// word这个单词之前加入过几次
		public int search(String word) {
			if (word == null) {
				return 0;
			}
			char[] chs = word.toCharArray();
			Node2 node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = (int) chs[i];
				if (!node.nexts.containsKey(index)) {
					return 0;
				}
				node = node.nexts.get(index);
			}
			return node.end;
		}

		// 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
		public int prefixNumber(String pre) {
			if (pre == null) {
				return 0;
			}
			char[] chs = pre.toCharArray();
			Node2 node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = (int) chs[i];
				if (!node.nexts.containsKey(index)) {
					return 0;
				}
				node = node.nexts.get(index);
			}
			return node.pass;
		}
	}

	public static class Right {

		private HashMap<String, Integer> box;

		public Right() {
			box = new HashMap<>();
		}

		public void insert(String word) {
			if (!box.containsKey(word)) {
				box.put(word, 1);
			} else {
				box.put(word, box.get(word) + 1);
			}
		}

		public void delete(String word) {
			if (box.containsKey(word)) {
				if (box.get(word) == 1) {
					box.remove(word);
				} else {
					box.put(word, box.get(word) - 1);
				}
			}
		}

		public int search(String word) {
			if (!box.containsKey(word)) {
				return 0;
			} else {
				return box.get(word);
			}
		}

		public int prefixNumber(String pre) {
			int count = 0;
			for (String cur : box.keySet()) {
				if (cur.startsWith(pre)) {
					count += box.get(cur);
				}
			}
			return count;
		}
	}

	// for test
	public static String generateRandomString(int strLen) {
		char[] ans = new char[(int) (Math.random() * strLen) + 1];
		for (int i = 0; i < ans.length; i++) {
			int value = (int) (Math.random() * 6);
			ans[i] = (char) (97 + value);
		}
		return String.valueOf(ans);
	}

	// for test
	public static String[] generateRandomStringArray(int arrLen, int strLen) {
		String[] ans = new String[(int) (Math.random() * arrLen) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = generateRandomString(strLen);
		}
		return ans;
	}

	public static void main(String[] args) {
		int arrLen = 100;
		int strLen = 20;
		int testTimes = 100000;
		for (int i = 0; i < testTimes; i++) {
			String[] arr = generateRandomStringArray(arrLen, strLen);
			Trie1 trie1 = new Trie1();
			Trie2 trie2 = new Trie2();
			Right right = new Right();
			for (int j = 0; j < arr.length; j++) {
				double decide = Math.random();
				if (decide < 0.25) {
					trie1.insert(arr[j]);
					trie2.insert(arr[j]);
					right.insert(arr[j]);
				} else if (decide < 0.5) {
					trie1.delete(arr[j]);
					trie2.delete(arr[j]);
					right.delete(arr[j]);
				} else if (decide < 0.75) {
					int ans1 = trie1.search(arr[j]);
					int ans2 = trie2.search(arr[j]);
					int ans3 = right.search(arr[j]);
					if (ans1 != ans2 || ans2 != ans3) {
						System.out.println("Oops!");
					}
				} else {
					int ans1 = trie1.prefixNumber(arr[j]);
					int ans2 = trie2.prefixNumber(arr[j]);
					int ans3 = right.prefixNumber(arr[j]);
					if (ans1 != ans2 || ans2 != ans3) {
						System.out.println("Oops!");
					}
				}
			}
		}
		System.out.println("finish!");

	}

}
