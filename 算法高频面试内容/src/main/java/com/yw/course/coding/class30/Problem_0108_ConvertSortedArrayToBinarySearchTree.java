package com.yw.course.coding.class30;

import com.yw.entity.TreeNode;

/**
 * @author yangwei
 */
public class Problem_0108_ConvertSortedArrayToBinarySearchTree {

	public TreeNode sortedArrayToBST(int[] nums) {
		if (nums == null || nums.length == 0) return null;
		return createBst(nums, 0, nums.length - 1);
	}
	private TreeNode createBst(int[] nums, int l, int r) {
		if (l > r) return null;
		if (l == r) return new TreeNode(nums[l]);
		int mid = (l + r) / 2;
		TreeNode root = new TreeNode(nums[mid]);
		root.left = createBst(nums, l, mid - 1);
		root.right = createBst(nums, mid + 1, r);
		return root;
	}

}
