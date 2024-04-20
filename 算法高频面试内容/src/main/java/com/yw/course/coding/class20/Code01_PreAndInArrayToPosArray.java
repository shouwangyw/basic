package com.yw.course.coding.class20;

import com.yw.entity.TreeNode;

import java.util.*;

import static com.yw.util.CommonUtils.isEqual;

/**
 * @author yangwei
 */
public class Code01_PreAndInArrayToPosArray {

	public static int[] genPostByPreAndIn(int[] pre, int[] in) {
		if (pre == null || in == null || pre.length != in.length) return null;
		int n = pre.length;
		Map<Integer, Integer> inMap = new HashMap<>();
		for (int i = 0; i < n; i++) {
			inMap.put(in[i], i);
		}
		int[] post = new int[n];
		process(pre, 0, n - 1, in, 0, n - 1, post, 0, n - 1, inMap);
		return post;
	}
	private static void process(int[] pre, int l1, int r1, int[] in, int l2, int r2, int[] post, int l3, int r3,
						 Map<Integer, Integer> inMap) {
		if (l1 > r1) return;
		if (l1 == r1) post[l3] = pre[l1];
		else {
			post[r3] = pre[l1];
			int idx = inMap.get(pre[l1]);
			process(pre, l1 + 1, l1 + idx - l2, in, l2, idx - 1, post, l3, l3 + idx - l2 - 1, inMap);
			process(pre, l1 + idx - l2 + 1, r1, in, idx + 1, r2, post, l3 + idx - l2, r3 - 1, inMap);
		}
	}

	public static int[] preInToPos1(int[] pre, int[] in) {
		if (pre == null || in == null || pre.length != in.length) {
			return null;
		}
		int N = pre.length;
		int[] pos = new int[N];
		process1(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1);
		return pos;
	}

	// L1...R1 L2...R2 L3...R3
	public static void process1(int[] pre, int L1, int R1, int[] in, int L2, int R2, int[] pos, int L3, int R3) {
		if (L1 > R1) {
			return;
		}
		if (L1 == R1) {
			pos[L3] = pre[L1];
			return;
		}
		pos[R3] = pre[L1];
		int mid = L2;
		for (; mid <= R2; mid++) {
			if (in[mid] == pre[L1]) {
				break;
			}
		}
		int leftSize = mid - L2;
		process1(pre, L1 + 1, L1 + leftSize, in, L2, mid - 1, pos, L3, L3 + leftSize - 1);
		process1(pre, L1 + leftSize + 1, R1, in, mid + 1, R2, pos, L3 + leftSize, R3 - 1);
	}

	public static void main(String[] args) {
		System.out.println("test begin");
		int maxLevel = 5;
		int value = 1000;
		int testTime = 100000;
		for (int i = 0; i < testTime; i++) {
			TreeNode root = generateRandomTree(value, maxLevel);
			int[] pre = getPreArray(root);
			int[] in = getInArray(root);
			int[] pos = getPosArray(root);
			int[] ans1 = preInToPos1(pre, in);
			int[] ans2 = genPostByPreAndIn(pre, in);
			if (!isEqual(pos, ans1) || !isEqual(ans1, ans2)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test end");
	}
	private static int[] getPreArray(TreeNode root) {
		List<Integer> arr = new ArrayList<>();
		fillPreArray(root, arr);
		int[] ans = new int[arr.size()];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = arr.get(i);
		}
		return ans;
	}
	private static void fillPreArray(TreeNode root, List<Integer> arr) {
		if (root == null) {
			return;
		}
		arr.add(root.val);
		fillPreArray(root.left, arr);
		fillPreArray(root.right, arr);
	}
	private static int[] getInArray(TreeNode root) {
		List<Integer> arr = new ArrayList<>();
		fillInArray(root, arr);
		int[] ans = new int[arr.size()];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = arr.get(i);
		}
		return ans;
	}
	private static void fillInArray(TreeNode root, List<Integer> arr) {
		if (root == null) {
			return;
		}
		fillInArray(root.left, arr);
		arr.add(root.val);
		fillInArray(root.right, arr);
	}
	private static int[] getPosArray(TreeNode root) {
		List<Integer> arr = new ArrayList<>();
		fillPostArray(root, arr);
		int[] ans = new int[arr.size()];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = arr.get(i);
		}
		return ans;
	}
	private static void fillPostArray(TreeNode root, List<Integer> arr) {
		if (root == null) {
			return;
		}
		fillPostArray(root.left, arr);
		fillPostArray(root.right, arr);
		arr.add(root.val);
	}
	private static TreeNode generateRandomTree(int value, int maxLevel) {
		Set<Integer> hasValue = new HashSet<>();
		return createTree(value, 1, maxLevel, hasValue);
	}
	private static TreeNode createTree(int value, int level, int maxLevel, Set<Integer> hasValue) {
		if (level > maxLevel) {
			return null;
		}
		int cur = 0;
		do {
			cur = (int) (Math.random() * value) + 1;
		} while (hasValue.contains(cur));
		hasValue.add(cur);
		TreeNode root = new TreeNode(cur);
		root.left = createTree(value, level + 1, maxLevel, hasValue);
		root.right = createTree(value, level + 1, maxLevel, hasValue);
		return root;
	}
}
