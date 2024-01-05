package com.yw.course.advance.class37;

import java.util.HashSet;
import java.util.Set;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code01_CountOfRangeSum {

	public static int countRangeSum1(int[] nums, int lower, int upper) {
		int n = nums.length;
		long[] sums = new long[n + 1];
		for (int i = 0; i < n; ++i)
			sums[i + 1] = sums[i] + nums[i];
		return countWhileMergeSort(sums, 0, n + 1, lower, upper);
	}

	private static int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {
		if (end - start <= 1)
			return 0;
		int mid = (start + end) / 2;
		int count = countWhileMergeSort(sums, start, mid, lower, upper)
				+ countWhileMergeSort(sums, mid, end, lower, upper);
		int j = mid, k = mid, t = mid;
		long[] cache = new long[end - start];
		for (int i = start, r = 0; i < mid; ++i, ++r) {
			while (k < end && sums[k] - sums[i] < lower)
				k++;
			while (j < end && sums[j] - sums[i] <= upper)
				j++;
			while (t < end && sums[t] < sums[i])
				cache[r++] = sums[t++];
			cache[r] = sums[i];
			count += j - k;
		}
		System.arraycopy(cache, 0, sums, start, t - start);
		return count;
	}

	public static class SBTNode {
		private long key;
		private SBTNode l;
		private SBTNode r;
		private long size; 	// 不同key的size
		private long all; 	// 总的size
		public SBTNode(long k) {
			this.key = k;
			this.size = 1;
			this.all = 1;
		}
	}

	public static class SizeBalancedTreeSet {
		private SBTNode root;
		private Set<Long> set = new HashSet<>();

		private SBTNode rightRotate(SBTNode cur) {
			long same = cur.all - (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
			SBTNode leftNode = cur.l;
			assert leftNode != null;
			cur.l = leftNode.r;
			leftNode.r = cur;
			leftNode.size = cur.size;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
			// all modify
			leftNode.all = cur.all;
			cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + same;
			return leftNode;
		}

		private SBTNode leftRotate(SBTNode cur) {
			long same = cur.all - (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
			SBTNode rightNode = cur.r;
			assert rightNode != null;
			cur.r = rightNode.l;
			rightNode.l = cur;
			rightNode.size = cur.size;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
			// all modify
			rightNode.all = cur.all;
			cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + same;
			return rightNode;
		}

		private SBTNode reBalance(SBTNode cur) {
			if (cur == null) return null;
			long leftSize = cur.l != null ? cur.l.size : 0;
			long leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
			long leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
			long rightSize = cur.r != null ? cur.r.size : 0;
			long rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
			long rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
			if (leftLeftSize > rightSize) {
				cur = rightRotate(cur);
				cur.r = reBalance(cur.r);
				cur = reBalance(cur);
			} else if (leftRightSize > rightSize) {
				cur.l = leftRotate(cur.l);
				cur = rightRotate(cur);
				cur.l = reBalance(cur.l);
				cur.r = reBalance(cur.r);
				cur = reBalance(cur);
			} else if (rightRightSize > leftSize) {
				cur = leftRotate(cur);
				cur.l = reBalance(cur.l);
				cur = reBalance(cur);
			} else if (rightLeftSize > leftSize) {
				cur.r = rightRotate(cur.r);
				cur = leftRotate(cur);
				cur.l = reBalance(cur.l);
				cur.r = reBalance(cur.r);
				cur = reBalance(cur);
			}
			return cur;
		}

		private SBTNode add(SBTNode cur, long key, boolean contains) {
			if (cur == null) return new SBTNode(key);
			cur.all++;
			if (key == cur.key) {
				return cur;
			} else { // 还在左滑或者右滑
				if (!contains) cur.size++;
				if (key < cur.key) cur.l = add(cur.l, key, contains);
				else cur.r = add(cur.r, key, contains);
				return reBalance(cur);
			}
		}

		public void add(long sum) {
			boolean contains = set.contains(sum);
			root = add(root, sum, contains);
			set.add(sum);
		}

		public long lessKeySize(long key) {
			SBTNode cur = root;
			long ans = 0;
			while (cur != null) {
				if (key == cur.key) {
					return ans + (cur.l != null ? cur.l.all : 0);
				} else if (key < cur.key) {
					cur = cur.l;
				} else {
					ans += cur.all - (cur.r != null ? cur.r.all : 0);
					cur = cur.r;
				}
			}
			return ans;
		}

		// > 7 8...
		// <8 ...<=7
		public long moreKeySize(long key) {
			return root != null ? (root.all - lessKeySize(key + 1)) : 0;
		}
	}

	public static int countRangeSum(int[] nums, int lower, int upper) {
		// 黑盒，加入数字（前缀和），不去重，可以接受重复数字
		// 支持查询 < num 有几个数？
		SizeBalancedTreeSet treeSet = new SizeBalancedTreeSet();
		long sum = 0;
		int ans = 0;
		treeSet.add(0);// 一个数都没有的时候，就已经有一个前缀和累加和为0
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
			// 求之前有多少个前缀和落在 [sum-upper, sum-lower] 范围上
			// 比如求 [10, 20] 范围，就可以先求 <10 有几个，<21 有几个，两个相减就得出有多少个前缀和落在[10, 20] 范围上
			ans += treeSet.lessKeySize(sum - lower + 1) - treeSet.lessKeySize(sum - upper);
			treeSet.add(sum);
		}
		return ans;
	}

	public static void main(String[] args) {
		int len = 200;
		int varible = 50;
		for (int i = 0; i < 10000; i++) {
			int[] test = generateArray(len, varible);
			int lower = (int) (Math.random() * varible) - (int) (Math.random() * varible);
			int upper = lower + (int) (Math.random() * varible);
			int ans1 = countRangeSum1(test, lower, upper);
			int ans2 = countRangeSum(test, lower, upper);
			if (ans1 != ans2) {
				printArray(test);
				System.out.println(lower);
				System.out.println(upper);
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}

	}

	public static int[] generateArray(int len, int varible) {
		int[] arr = new int[len];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * varible);
		}
		return arr;
	}

}
