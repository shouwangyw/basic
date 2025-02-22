package com.yw.course.coding.class37;

import com.yw.entity.TreeNode;

public class Problem_0226_InvertBinaryTree {

	public TreeNode invertTree(TreeNode root) {
		if (root == null) return null;
		TreeNode right = invertTree(root.left);
		root.left = invertTree(root.right);
		root.right = right;
		return root;
	}

}
