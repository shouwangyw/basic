package com.yw.course.coding.class06;

import java.util.Arrays;

/**
 * 测试链接 : https://leetcode.cn/problems/maximum-xor-with-an-element-from-array/
 * @author yangwei
 */
public class Code03_MaximumXorWithAnElementFromArray {

	// 方法一：暴力解，会超出时间限制
	public static int[] maximizeXor0(int[] nums, int[][] queries) {
		int n = queries.length;
		int[] ans = new int[n];
		Arrays.sort(nums);
		for (int i = 0; i < n; i++) ans[i] = maximizeXor(nums, queries[i][0], queries[i][1]);
		return ans;
	}
	private static int maximizeXor(int[] nums, int x, int m) {
		int max = -1;
		for (int i = 0; i < nums.length && nums[i] <= m; i++) {
			max = Math.max(max, x ^ nums[i]);
		}
		return max;
	}

	public static void main(String[] args) {
//		int[] nums = {0,1,2,3,4};
		int[] nums = {5,2,4,6,6,3};
//		int[][] queries = {{3,1},{1,3},{5,6}};
		int[][] queries = {{12,4},{8,1},{6,3}};
		System.out.println(Arrays.toString(maximizeXor0(nums, queries)));
	}

	// 方法二：前缀树
	public int[] maximizeXor(int[] nums, int[][] queries) {
		int n = nums.length, m = queries.length;
		// 构建前缀树
		NumTrie trie = new NumTrie();
		for (int x : nums) trie.add(x);
		int[] ans = new int[m];
		for (int i = 0; i < m; i++) ans[i] = trie.maxXorLessThenOrEqualToM(queries[i][0], queries[i][1]);
		return ans;
	}
	private static class Node {
		private int min;
		private Node[] nexts;
		public Node() {
			this.min = Integer.MAX_VALUE;
			this.nexts = new Node[2];
		}
	}
	private static class NumTrie {
		private Node head = new Node();
		public void add(int num) {
			Node cur = head;
			head.min = Math.min(head.min, num);
			for (int i = 30; i >= 0; i--) {
				int path = (num >> i) & 1;
				if (cur.nexts[path] == null) cur.nexts[path] = new Node(); // 没路，就创建节点
				cur = cur.nexts[path];
				cur.min = Math.min(cur.min, num);
			}
		}
		// 返回不超过m并且与x的最大异或值
		public int maxXorLessThenOrEqualToM(int x, int m) {
			if (head.min > m) return -1;
			Node cur = head;
			int max = 0;
			for (int i = 30; i >= 0; i--) {
				int path = (x >> i) & 1;
				int best = path ^ 1;
				// 没路或这条路上最小值都大于m了，就走相反的路
				if (cur.nexts[best] == null || cur.nexts[best].min > m) best ^= 1;
				max |= (path ^ best) << i;
				cur = cur.nexts[best];
			}
			return max;
		}
	}

}
