package com.yw.course.advance.class30;

import com.yw.entity.TreeNode;

import static com.yw.util.CommonUtils.printTree;

/**
 * @author yangwei
 */
public class Code01_MorrisTraversal {

	public static void process(TreeNode root) {
		if (root == null) return;
		// 1 前序
		process(root.left);
		// 2 中序
		process(root.right);
		// 3 后序
	}

	// morris遍历
	public static void morris(TreeNode root) {
		if (root == null) return;
		TreeNode cur = root, mostRight;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) mostRight = mostRight.right;
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else mostRight.right = null;
			}
			cur = cur.right;
		}
	}

	// morris前序遍历
	public static void morrisPre(TreeNode root) {
		if (root == null) return;
		TreeNode cur = root, mostRight;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) mostRight = mostRight.right;
				if (mostRight.right == null) {
					// 第1次访问到当前节点时打印
					System.out.print(cur.val + " ");
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else mostRight.right = null;
			} else {
				// 第1次访问到当前节点时打印
				System.out.print(cur.val + " ");
			}
			cur = cur.right;
		}
		System.out.println();
	}
	// morris中序遍历
	public static void morrisIn(TreeNode root) {
		if (root == null) return;
		TreeNode cur = root, mostRight;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) mostRight = mostRight.right;
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else mostRight.right = null;
			}
			// 第2次访问到当前节点时打印
			System.out.print(cur.val + " ");
			cur = cur.right;
		}
		System.out.println();
	}
	// morris后序遍历
	public static void morrisPost(TreeNode root) {
		if (root == null) return;
		TreeNode cur = root, mostRight;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) mostRight = mostRight.right;
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
					// 第2次访问到当前节点时，逆序打印左树的右边界
					reversePrintEdge(cur.left);
				}
			}
			cur = cur.right;
		}
		// 最后打印整体右边界
		reversePrintEdge(root);
		System.out.println();
	}
	private static void reversePrintEdge(TreeNode root) {
		TreeNode node = reverseNode(root), cur = node;
		while (cur != null) {
			System.out.print(cur.val + " ");
			cur = cur.right;
		}
		reverseNode(node);
	}
	private static TreeNode reverseNode(TreeNode node) {
		TreeNode pre = null, next;
		while (node != null) {
			next = node.right;
			node.right = pre;
			pre = node;
			node = next;
		}
		return pre;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(4);
		root.left = new TreeNode(2);
		root.right = new TreeNode(6);
		root.left.left = new TreeNode(1);
		root.left.right = new TreeNode(3);
		root.right.left = new TreeNode(5);
		root.right.right = new TreeNode(7);
		printTree(root);
		morrisPre(root);
		morrisIn(root);
		morrisPost(root);
		printTree(root);
	}
}
