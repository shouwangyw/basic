package com.yw.course.basic.class06;

import com.yw.entity.TreeNode;

/**
 * 测试链接：https://leetcode.cn/problems/same-tree
 * @author yangwei
 */
public class Code02_SameTree {

	public boolean isSameTree(TreeNode p, TreeNode q) {
		if (p == null && q == null) return true;
		if (p == null || q == null) return false;
		return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
	}
}
