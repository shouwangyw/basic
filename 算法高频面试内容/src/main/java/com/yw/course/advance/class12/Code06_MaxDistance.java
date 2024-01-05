package com.yw.course.advance.class12;

import com.yw.entity.TreeNode;

import java.util.*;

import static com.yw.util.CommonUtils.generateRandomBST;

/**
 * @author yangwei
 */
public class Code06_MaxDistance {

	// 方法一：暴力穷举任意两个节点之间的距离
	public static int getMaxDistance(TreeNode root) {
		if (root == null) return 0;
		// 先序遍历，拿到所有节点 List
		List<TreeNode> nodes = new ArrayList<>();
		preOrder(root, nodes);
		// 获取每个节点与父节点的映射关系 Map
		Map<TreeNode, TreeNode> nodeMap = new HashMap<>();
		fillNodeMap(root, nodeMap);
		// 暴力求两个节点之间的距离，从中求一个最大值
		int max = 0;
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = i; j < nodes.size(); j++) {
				max = Math.max(max, getDistance(nodes.get(i), nodes.get(j), nodeMap));
			}
		}
		return max;
	}
	private static void preOrder(TreeNode root, List<TreeNode> nodes) {
		if (root == null) return;
		nodes.add(root);
		preOrder(root.left, nodes);
		preOrder(root.right, nodes);
	}
	private static void fillNodeMap(TreeNode root, Map<TreeNode, TreeNode> nodeMap) {
		if (root == null) return;
		if (root.left != null) {
			nodeMap.put(root.left, root);
			fillNodeMap(root.left, nodeMap);
		}
		if (root.right != null) {
			nodeMap.put(root.right, root);
			fillNodeMap(root.right, nodeMap);
		}
	}
	private static int getDistance(TreeNode node1, TreeNode node2, Map<TreeNode, TreeNode> nodeMap) {
		// 先拿到node1的所有祖先节点
		Set<TreeNode> nodeSet = new HashSet<>();
		nodeSet.add(node1);
		TreeNode cur = node1;
		while (nodeMap.containsKey(cur)) {
			cur = nodeMap.get(cur);
			nodeSet.add(cur);
		}
		// 获取最近公共祖先节点
		TreeNode lowestAncestor = node2;
		while (!nodeSet.contains(lowestAncestor)) lowestAncestor = nodeMap.get(lowestAncestor);
		// 分别求node1、node2到公共祖先节点的距离
		int dis1 = 1, dis2 = 1;
		cur = node1;
		while (cur != lowestAncestor) {
			cur = nodeMap.get(cur);
			dis1++;
		}
		cur = node2;
		while (cur != lowestAncestor) {
			cur = nodeMap.get(cur);
			dis2++;
		}
		return dis1 + dis2 - 1;
	}
	// 方法二：基于递归套路
	public static int maxDistance(TreeNode root) {
		return process(root).maxDistance;
	}
	// 1. 封装信息
	private static class Info {
		int maxDistance;
		int height;
		public Info(int maxDistance, int height) {
			this.maxDistance = maxDistance;
			this.height = height;
		}
	}
	// 2. 处理递归
	private static Info process(TreeNode root) {
		if (root == null) return new Info(0, 0);
		Info leftInfo = process(root.left);
		Info rightInfo = process(root.right);

		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		/**
		 * 1. 左子树 maxDistance
		 * 2. 右子树 maxDistance
		 * 3. 左子树最远(maxHeight) + 根节点 + 右子树最远(maxHeight)
		 * 三者求一个最大值
		 */
		int maxDistance = Math.max(Math.max(leftInfo.maxDistance, rightInfo.maxDistance),
				leftInfo.height + rightInfo.height + 1);

		return new Info(maxDistance, height);
	}

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			TreeNode root = generateRandomBST(maxLevel, maxValue);
			int ans1 = getMaxDistance(root);
			int ans2 = maxDistance(root);
			if (ans1 != ans2) {
				System.out.println(ans1 + " " + ans2);
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}
}
