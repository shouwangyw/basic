package com.yw.advance.course.class27;

import com.yw.entity.TreeNode;

import java.util.ArrayList;
import java.util.List;

import static com.yw.util.CommonUtils.generateRandomBST;
import static com.yw.util.CommonUtils.isEqual;

/**
 * @author yangwei
 */
public class Code02_TreeEqual {

	// 方法一：递归
	public static boolean containsTree(TreeNode big, TreeNode small) {
		if (small == null) return true;
		if (big == null) return false;
		if (isSame(big, small)) return true;
		return containsTree(big.left, small) || containsTree(big.right, small);
	}
	private static boolean isSame(TreeNode root1, TreeNode root2) {
		if (root1 == null && root2 == null) return true;
		if (root1 == null || root2 == null) return false;
		return root1.val == root2.val && isSame(root1.left, root2.left) && isSame(root1.right, root2.right);
	}

	// 方法二：序列化二叉树+KMP匹配
	public static boolean containsTreeByKmp(TreeNode big, TreeNode small) {
		if (small == null) return true;
		if (big == null) return false;
		List<String> s = new ArrayList<>(), m = new ArrayList<>();
		preOrder(big, s);
		preOrder(small, m);
		return indexOf(s.toArray(new String[0]), m.toArray(new String[0])) != -1;
	}
	private static void preOrder(TreeNode root, List<String> pre) {
		if (root == null) {
			pre.add(null);
			return;
		}
		pre.add(String.valueOf(root.val));
		preOrder(root.left, pre);
		preOrder(root.right, pre);
	}
	private static int indexOf(String[] ss, String[] ms) {
		int[] next = getNext(ms);
		int i = 0, j = 0;
		while (i < ss.length && j < ms.length) {
			if (isEqual(ss[i], ms[j])) {
				i++;
				j++;
			} else if (next[j] == -1) i++;
			else j = next[j];
		}
		return j == ms.length ? i - j : -1;
	}
	private static int[] getNext(String[] ms) {
		int[] next = new int[ms.length];
		next[0] = -1;
		if (ms.length == 1) return next;
		int i = 2, j = 0;
		while (i < ms.length) {
			if (isEqual(ms[i - 1], ms[j])) next[i++] = ++j;
			else if (j > 0) j = next[j];
			else next[i++] = 0;
		}
		return next;
	}

	public static void main(String[] args) {
		int bigTreeLevel = 7;
		int smallTreeLevel = 4;
		int nodeMaxValue = 5;
		int testTimes = 100000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			TreeNode big = generateRandomBST(bigTreeLevel, nodeMaxValue);
			TreeNode small = generateRandomBST(smallTreeLevel, nodeMaxValue);
			boolean ans1 = containsTree(big, small);
			boolean ans2 = containsTreeByKmp(big, small);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("test finish!");
	}
}
