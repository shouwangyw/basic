package com.yw.basic.course.class06;

import com.yw.entity.TreeNode;

/**
 * 测试链接：https://leetcode.cn/problems/maximum-depth-of-binary-tree
 *
 * @author yangwei
 */
public class Code04_MaximumDepthOfBinaryTree {

    public int maxDepth(TreeNode root) {
        return root == null ? 0 : (Math.max(maxDepth(root.left), maxDepth(root.right)) + 1);
    }
}
