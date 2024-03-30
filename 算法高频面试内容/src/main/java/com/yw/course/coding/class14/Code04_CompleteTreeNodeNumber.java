package com.yw.course.coding.class14;

import com.yw.entity.TreeNode;

/**
 * @author yangwei
 */
public class Code04_CompleteTreeNodeNumber {

	// 时间复杂度 O((logN)^2)
	public static int getNodeNum(TreeNode root) {
		return root == null ? 0 : getNodeNum(root, 1, mostLeftLevel(root, 1));
	}
	// 当前来到node节点，node节点在第level层，总层数是h
	// 返回以node为头节点的子树(必是完全二叉树)有多少节点
	private static int getNodeNum(TreeNode node, int level, int h) {
		if (level == h) return 1;
		return (mostLeftLevel(node.right, level + 1) == h)
				? (1 << (h - level)) + getNodeNum(node.right, level + 1, h)
				: (1 << (h - level - 1)) + getNodeNum(node.left, level + 1, h);
	}
	// node在第level层，求以node为头的子树(必是完全二叉树)，最大深度是多少
	private static int mostLeftLevel(TreeNode node, int level) {
		while (node != null) {
			level++;
			node = node.left;
		}
		return level - 1;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		System.out.println(getNodeNum(root));

	}

}
