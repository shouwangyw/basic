package com.yw.basic.course.class06;

import com.yw.entity.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试链接：https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal
 *
 * @author yangwei
 */
public class Code05_ConstructBinaryTreeFromPreorderAndInorderTraversal {

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int len = preorder.length;
        Map<Integer, Integer> map = new HashMap<>(len);
        // 记下每个中序序列值的位置
        for (int i = 0; i < len; i++) map.put(inorder[i], i);
        return buildTree(preorder, 0, len - 1, inorder, 0, len - 1, map);
    }

    private TreeNode buildTree(int[] pre, int l1, int r1, int[] in, int l2, int r2, Map<Integer, Integer> map) {
        if (l1 > r1) return null;
        TreeNode root = new TreeNode(pre[l1]);
        if (l1 == r1) return root;
        int k = map.get(pre[l1]);
        root.left = buildTree(pre, l1 + 1, l1 + k - l2, in, l2, k - 1, map);
        root.right = buildTree(pre, l1 + k - l2 + 1, r1, in, k + 1, r2, map);
        return root;
    }
}
