package com.yw.course.coding.class03;

import com.yw.entity.TreeNode;

import java.util.*;

/**
 * @author yangwei
 */
public class Code08_DistanceKNodes {

	public static List<TreeNode> distanceKNodes(TreeNode root, TreeNode target, int k) {
		Map<TreeNode, TreeNode> parentMap = new HashMap<>();
		// 用一个Map为每个节点维护一个父节点
		createParentMap(root, parentMap);
		// 从target节点出发进行宽度优先遍历
		Queue<TreeNode> queue = new LinkedList<>();
		Set<TreeNode> visited = new HashSet<>();
		queue.offer(target);
		visited.add(target);
		while (!queue.isEmpty() && (k-- > 0)) {
			int size = queue.size();
			while (size-- > 0) {
				TreeNode curNode = queue.poll();
				if (curNode == null) continue;
				TreeNode[] nodes = {curNode.left, curNode.right, parentMap.get(curNode)};
				for (TreeNode node : nodes) {
					if (node == null || visited.contains(node)) continue;
					queue.offer(node);
					visited.add(node);
				}
			}
		}
		// 最后剩下来的就是到target距离k的节点
		return new ArrayList<>(queue);
	}
	private static void createParentMap(TreeNode node, Map<TreeNode, TreeNode> parentMap) {
		if (node == null) return;
		if (node.left != null) {
			parentMap.put(node.left, node);
			createParentMap(node.left, parentMap);
		}
		if (node.right != null) {
			parentMap.put(node.right, node);
			createParentMap(node.right, parentMap);
		}
	}

	public static void main(String[] args) {
		TreeNode n0 = new TreeNode(0);
		TreeNode n1 = new TreeNode(1);
		TreeNode n2 = new TreeNode(2);
		TreeNode n3 = new TreeNode(3);
		TreeNode n4 = new TreeNode(4);
		TreeNode n5 = new TreeNode(5);
		TreeNode n6 = new TreeNode(6);
		TreeNode n7 = new TreeNode(7);
		TreeNode n8 = new TreeNode(8);

		n3.left = n5;
		n3.right = n1;
		n5.left = n6;
		n5.right = n2;
		n1.left = n0;
		n1.right = n8;
		n2.left = n7;
		n2.right = n4;

		TreeNode root = n3;
		TreeNode target = n5;
		int K = 2;

		List<TreeNode> ans = distanceKNodes(root, target, K);
		for (TreeNode o1 : ans) {
			System.out.println(o1.val);
		}

	}

}
