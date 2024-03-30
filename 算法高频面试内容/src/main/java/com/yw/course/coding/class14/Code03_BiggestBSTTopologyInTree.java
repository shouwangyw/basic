package com.yw.course.coding.class14;

import java.util.Scanner;

/**
 * 测试链接 : https://www.nowcoder.com/practice/e13bceaca5b14860b83cbcc4912c5d4a
 * @author yangwei
 */
public class Code03_BiggestBSTTopologyInTree {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt(), root = in.nextInt();
		// 用一个二维数组作为二叉树结构
		int[][] tree = new int[n + 1][3];
		for (int i = 1; i <= n; i++) {
			int c = in.nextInt(), l = in.nextInt(), r = in.nextInt();
			tree[l][0] = c;
			tree[r][0] = c;
			tree[c][1] = l;
			tree[c][2] = r;
		}
		System.out.println(getMaxBstTopology(tree, root, new int[n + 1]));
		in.close();
	}
	// tree: 二叉树 tree[i][0]表示i节点的父节点、tree[i][1]表示i节点的左孩子、tree[i][2]表示i节点的右孩子
	// map: map[i]表示以i节点为根的最大bst拓扑结构的大小
	// 返回以root为根的整颗二叉树的最大bst拓扑结构的大小
	private static int getMaxBstTopology(int[][] tree, int root, int[] map) {
		// base case
		if (root == 0) return 0;
		// 拿到左右子树的信息
		int left = tree[root][1], right = tree[root][2], c;
		int leftVal = getMaxBstTopology(tree, left, map), rightVal = getMaxBstTopology(tree, right, map);
		// 整合出自己的信息
		// 1. 找左子树的右边界: 只要满足BST(left < root)，并且有贡献度(map[left] != 0)，就一直往右子树找
		while (left < root && map[left] != 0) left = tree[left][2];
		if (map[left] != 0) { // 进到这里，说明 left >= root 了
			c = map[left];
			while (left != root) {
				map[left] -= c;
				left = tree[left][0];
			}
		}
		// 2. 找右子树的左边界: 只要满足BST(right > root)，并且有贡献度(map[right] != 0)，就一直往左子树找
		while (right > root && map[right] != 0) right = tree[right][1];
		if (map[right] != 0) { // 进到这里，说明 right <= root 了
			c = map[right];
			while (right != root) {
				map[right] -= c;
				right = tree[right][0];
			}
		}
		return Math.max(Math.max(leftVal, rightVal), map[root] = map[tree[root][1]] + map[tree[root][2]] + 1);
	}
}
