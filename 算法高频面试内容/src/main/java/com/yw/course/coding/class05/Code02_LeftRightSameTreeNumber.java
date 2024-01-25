package com.yw.course.coding.class05;

import com.yw.entity.TreeNode;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 如果一个节点X，它左树结构和右树结构完全一样，那么我们说以X为头的子树是相等子树
 * 给定一棵二叉树的头节点head，返回head整棵树上有多少棵相等子树
 * @author yangwei
 */
public class Code02_LeftRightSameTreeNumber {

	// 方法一：时间复杂度O(N * logN)
	public static int equalSubTreeNums0(TreeNode root) {
		if (root == null) return 0;
		return equalSubTreeNums(root.left) + equalSubTreeNums(root.right) + (equals(root.left, root.right) ? 1 : 0);
	}
	private static boolean equals(TreeNode node1, TreeNode node2) {
		if (node1 == null && node2 == null) return true;
		if (node1 == null || node2 == null) return false;
		return node1.val == node2.val && equals(node1.left, node2.left) && equals(node1.right, node2.right);
	}

	// 方法二：递归套路+Hash函数，时间复杂度O(N)
	public static int equalSubTreeNums(TreeNode root) {
		return process(root).nums;
	}
	private static Info process(TreeNode root) {
		if (root == null) return new Info();
		Info leftInfo = process(root.left), rightInfo = process(root.right);
		int curNums = (leftInfo.uniqueKey.equals(rightInfo.uniqueKey) ? 1 : 0) + leftInfo.nums + rightInfo.nums;
		return new Info(curNums, root.val + "," + leftInfo.uniqueKey + rightInfo.uniqueKey);
	}
	private static class Hash {
		private MessageDigest hash;
		Hash(String algorithm){
			try {
				hash = MessageDigest.getInstance(algorithm);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		public String hashCode(String input) {
			return DatatypeConverter.printHexBinary(hash.digest(input.getBytes())).toUpperCase();
		}
	}
	private static class Info {
		private static final Hash HASH = new Hash("SHA-256");

		private int nums;
		private String uniqueKey;
		public Info() {
			this.nums = 0;
			this.uniqueKey = HASH.hashCode("#,");
		}
		public Info(int nums, String key) {
			this.nums = nums;
			this.uniqueKey = HASH.hashCode(key);
		}
	}

	public static void main(String[] args) {
		int maxLevel = 8;
		int maxValue = 4;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			TreeNode head = randomBinaryTree(maxLevel, maxValue);
			int ans1 = equalSubTreeNums0(head);
			int ans2 = equalSubTreeNums(head);
			if (ans1 != ans2) {
				System.out.println("出错了！");
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}
		System.out.println("测试结束");
	}
	private static TreeNode randomBinaryTree(int restLevel, int maxValue) {
		if (restLevel == 0) {
			return null;
		}
		TreeNode head = Math.random() < 0.2 ? null : new TreeNode((int) (Math.random() * maxValue));
		if (head != null) {
			head.left = randomBinaryTree(restLevel - 1, maxValue);
			head.right = randomBinaryTree(restLevel - 1, maxValue);
		}
		return head;
	}
}
