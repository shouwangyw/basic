package com.yw.advance.course.class11;

import com.yw.entity.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author yangwei
 */
public class Code01_LevelTraversalBT {

	// 层序遍历-基于队列实现
	public static void levelOrder(TreeNode root) {
		if (root == null) return;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
			System.out.print(node.val + " ");
			if (node.left != null) queue.offer(node.left);
			if (node.right != null) queue.offer(node.right);
		}
		System.out.println();
	}
	// 层序遍历-基于递归
	public static void levelOrder(TreeNode root, int level, List<List<Integer>> levelResults) {
		if (root == null) return;
		if (level == levelResults.size()) levelResults.add(new ArrayList<>());
		levelResults.get(level).add(root.val);
		levelOrder(root.left, level + 1, levelResults);
		levelOrder(root.right, level + 1, levelResults);
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);

		levelOrder(root);
		System.out.println("========");

		List<List<Integer>> levelResults = new ArrayList<>();
		levelOrder(root, 0, levelResults);
		for (List<Integer> results : levelResults) {
			for (Integer result : results) {
				System.out.print(result + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
