package com.yw.course.basic.class06;

import com.yw.entity.TreeNode;

/**
 * 测试链接：https://leetcode.cn/problems/symmetric-tree
 *
 * @author yangwei
 */
public class Code03_SymmetricTree {

    public boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    private boolean isMirror(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        return p.val == q.val && isMirror(p.left, q.right) && isMirror(p.right, q.left);
    }
}
