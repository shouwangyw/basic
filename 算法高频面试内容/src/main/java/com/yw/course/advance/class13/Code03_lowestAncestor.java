package com.yw.course.advance.class13;

import com.yw.entity.TreeNode;

import java.util.*;

import static com.yw.util.CommonUtils.generateRandomBST;
import static com.yw.util.CommonUtils.pickRandomOne;

/**
 * @author yangwei
 */
public class Code03_lowestAncestor {

	// 方法一：利用HashMap记录每个节点的父节点
	public static TreeNode lowestCommonAncestorByMap(TreeNode root, TreeNode node1, TreeNode node2) {
		if (root == null) return null;
		Map<TreeNode, TreeNode> nodeMap = new HashMap<>();
		fillNodeMap(root, nodeMap);
		// 先拿到node1的所有祖先节点
		Set<TreeNode> nodeSet = new HashSet<>();
		nodeSet.add(node1);
		TreeNode cur = node1;
		while (nodeMap.containsKey(cur)) {
			cur = nodeMap.get(cur);
			nodeSet.add(cur);
		}
		// 依次找node2的祖先节点，找到最近公共祖先节点
		cur = node2;
		while (!nodeSet.contains(cur)) {
			cur = nodeMap.get(cur);
		}
		return cur;
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
	// 方法一：基于递归套路
	public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode node1, TreeNode node2) {
		return process(root, node1, node2).lowestAncestor;
	}
	// 1. 封装信息
	private static class Info {
		boolean find1;
		boolean find2;
		TreeNode lowestAncestor;
		public Info(boolean find1, boolean find2, TreeNode lowestAncestor) {
			this.find1 = find1;
			this.find2 = find2;
			this.lowestAncestor = lowestAncestor;
		}
	}
	// 2. 处理递归
	private static Info process(TreeNode root, TreeNode node1, TreeNode node2) {
		if (root == null) return new Info(false, false, null);
		Info leftInfo = process(root.left, node1, node2);
		Info rightInfo = process(root.right, node1, node2);

		// 找到node1: 就是当前节点、在左子树上 或者 在右子树上
		boolean find1 = root == node1 || leftInfo.find1 || rightInfo.find1;
		// 找到node2: 就是当前节点、在左子树上 或者 在右子树上
		boolean find2 = root == node2 || leftInfo.find2 || rightInfo.find2;
		TreeNode lowestAncestor = null;
		if (leftInfo.lowestAncestor != null) lowestAncestor = leftInfo.lowestAncestor;
		else if (rightInfo.lowestAncestor != null) lowestAncestor = rightInfo.lowestAncestor;
		else if (find1 && find2) lowestAncestor = root;

		return new Info(find1, find2, lowestAncestor);
	}

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			TreeNode head = generateRandomBST(maxLevel, maxValue);
			TreeNode o1 = pickRandomOne(head);
			TreeNode o2 = pickRandomOne(head);
			if (lowestCommonAncestorByMap(head, o1, o2) != lowestCommonAncestor(head, o1, o2)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
