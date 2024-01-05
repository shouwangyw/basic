package com.yw.course.advance.class12;

import com.yw.entity.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * complete binary tree
 * @author yangwei
 */
public class Code01_IsCBT {
	// 方法一：基于队列实现
	public static boolean isCbtByQueue(TreeNode root) {
		if (root == null) return true;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		// 是否遇到过左右两个孩子不双全的节点，即只有左子树、或右子树、或都没有
		boolean leaf = false;
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
			TreeNode left = node.left;
			TreeNode right = node.right;
			// 1. 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
//			if (leaf && !(left == null && right == null)
				// 2. 发现了只有右子树的节点
//				|| (left == null && right != null)) return false;
			if (leaf && (left != null || right != null) || (left == null && right != null)) return false;
			if (left == null || right == null) leaf = true;
			if (left != null) queue.offer(left);
			if (right != null) queue.offer(right);
		}
		return true;
	}

//	public static boolean isCBT2(TreeNode root) {
//		if (root == null) {
//			return true;
//		}
//		return process(root).isCBT;
//	}
//
//	// 对每一棵子树，是否是满二叉树、是否是完全二叉树、高度
//	public static class Info {
//		public boolean isFull;
//		public boolean isCBT;
//		public int height;
//
//		public Info(boolean full, boolean cbt, int h) {
//			isFull = full;
//			isCBT = cbt;
//			height = h;
//		}
//	}
//
//	public static Info process(TreeNode X) {
//		if (X == null) {
//			return new Info(true, true, 0);
//		}
//		Info leftInfo = process(X.left);
//		Info rightInfo = process(X.right);
//
//
//
//		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
//
//
//		boolean isFull = leftInfo.isFull
//				&&
//				rightInfo.isFull
//				&& leftInfo.height == rightInfo.height;
//
//
//		boolean isCBT = false;
//		if (isFull) {
//			isCBT = true;
//		} else { // 以x为头整棵树，不满
//			if (leftInfo.isCBT && rightInfo.isCBT) {
//
//
//				if (leftInfo.isCBT
//						&& rightInfo.isFull
//						&& leftInfo.height == rightInfo.height + 1) {
//					isCBT = true;
//				}
//				if (leftInfo.isFull
//						&&
//						rightInfo.isFull
//						&& leftInfo.height == rightInfo.height + 1) {
//					isCBT = true;
//				}
//				if (leftInfo.isFull
//						&& rightInfo.isCBT && leftInfo.height == rightInfo.height) {
//					isCBT = true;
//				}
//
//
//			}
//		}
//		return new Info(isFull, isCBT, height);
//	}
}
