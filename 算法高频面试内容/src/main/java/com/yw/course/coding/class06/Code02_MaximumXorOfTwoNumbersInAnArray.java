package com.yw.course.coding.class06;

/**
 * 测试链接 : https://leetcode.cn/problems/maximum-xor-of-two-numbers-in-an-array/
 * @author yangwei
 */
public class Code02_MaximumXorOfTwoNumbersInAnArray {

	public int findMaximumXOR(int[] nums) {
		int ans = 0;
		NumTrie trie = new NumTrie();
		trie.add(nums[0]);
		for (int i = 1; i < nums.length; i++) {
			ans = Math.max(ans, trie.maxXor(nums[i]));
			trie.add(nums[i]);
		}
		return ans;
	}
	private static class Node {
		private Node[] nexts = new Node[2];
	}
	private static class NumTrie {
		private Node head = new Node();
		public void add(int num) {
			Node cur = head;
			for (int i = 31; i >= 0; i--) {
				int path = (num >> i) & 1;
				if (cur.nexts[path] == null) cur.nexts[path] = new Node();
				cur = cur.nexts[path];
			}
		}
		public int maxXor(int num) {
			Node cur = head;
			int max = 0;
			for (int i = 31; i >= 0; i--) {
				int path = (num >> i) & 1, best = i == 31 ? path : (path ^ 1);
				if (cur.nexts[best] == null) best ^= 1;
				max |= (path ^ best) << i;
				cur = cur.nexts[best];
			}
			return max;
		}
	}
}
