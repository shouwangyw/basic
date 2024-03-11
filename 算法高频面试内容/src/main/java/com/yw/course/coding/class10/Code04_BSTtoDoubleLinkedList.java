package com.yw.course.coding.class10;

import com.yw.entity.TreeNode;

/**
 * 测试链接 : https://leetcode.cn/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/
 * @author yangwei
 */
public class Code04_BSTtoDoubleLinkedList {

	private static class Info {
		private TreeNode start;
		private TreeNode end;
		public Info() { }
		public Info(TreeNode start, TreeNode end) {
			this.start = start;
			this.end = end;
		}
	}
	public static TreeNode treeToDoublyList(TreeNode root) {
		if (root == null) return null;
		Info info = process(root);
		info.end.right = info.start;
		info.start.left = info.end;
		return info.start;
	}
	private static Info process(TreeNode node) {
		if (node == null) return new Info();
		Info leftInfo = process(node.left), rightInfo = process(node.right);
		if (leftInfo.end != null) leftInfo.end.right = node;
		node.left = leftInfo.end;
		node.right = rightInfo.start;
		if (rightInfo.start != null) rightInfo.start.left = node;
		return new Info(leftInfo.start != null ? leftInfo.start : node,
				rightInfo.end != null ? rightInfo.end : node);
	}

}