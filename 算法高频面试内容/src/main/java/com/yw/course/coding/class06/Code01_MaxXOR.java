package com.yw.course.coding.class06;

import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code01_MaxXOR {

	// 方法一：暴力解 O(N^2)
	public static int maxXorSubArray0(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		// 准备一个前缀异或和数组
		int n = arr.length;
		int[] eor = new int[n];
		eor[0] = arr[0];
		for (int i = 1; i < n; i++) {
			eor[i] = eor[i - 1] ^ arr[i];
		}
		int max = Integer.MIN_VALUE;
		for (int j = 0; j < n; j++) {
			for (int i = 0; i <= j; i++) {
				// 依次尝试arr[0..j]、arr[1..j]..arr[i..j]..arr[j..j]
				max = Math.max(max, i == 0 ? eor[j] : eor[j] ^ eor[i - 1]);
			}
		}
		return max;
	}

	// 方法二：O(N)
	public static int maxXorSubArray(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		// xor: 0~i整体异或和
		int max = Integer.MIN_VALUE, xor = 0;
		NumTrie trie = new NumTrie();
		trie.add(0);
		for (int x : arr) {
			xor ^= x;
			max = Math.max(max, trie.maxXor(xor));
			trie.add(xor);
		}
		return max;
	}
	/**
	 * 定义前缀树结构
	 * nexts[0] 表示0方向的路，nexts[1] 表示1方向的路
	 * nexts[0] == null 表示0方向上没路，nexts[0] != null 表示0方向有路，可以跳下一个节点
	 * nexts[1] == null 表示1方向上没路，nexts[1] != null 表示1方向有路，可以跳下一个节点
	 */
	private static class Node {
		public Node[] nexts = new Node[2];
	}
	// 基于本题，定制前缀树的实现
	private static class NumTrie {
		// 头节点
		private Node head = new Node();
		public void add(int newNum) {
			Node cur = head;
			for (int move = 31; move >= 0; move--) {
				int path = ((newNum >> move) & 1);
				cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
				cur = cur.nexts[path];
			}
		}
		// 该结构之前收集了一票数字，并且建好了前缀树
		// num和 谁 ^ 最大的结果（把结果返回）
		public int maxXor(int num) {
			Node cur = head;
			int ans = 0;
			for (int move = 31; move >= 0; move--) {
				// 取出num中第move位的状态，path只有两种值0就1，整数
				int path = (num >> move) & 1;
				// 期待遇到的东西
				int best = move == 31 ? path : (path ^ 1);
				// 实际遇到的东西
				best = cur.nexts[best] != null ? best : (best ^ 1);
				// (path ^ best) 当前位位异或完的结果
				ans |= (path ^ best) << move;
				cur = cur.nexts[best];
			}
			return ans;
		}
	}

	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 30;
		int maxValue = 50;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int comp = maxXorSubArray0(arr);
			int res = maxXorSubArray(arr);
			if (res != comp) {
				succeed = false;
				printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}
	private static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}
}
