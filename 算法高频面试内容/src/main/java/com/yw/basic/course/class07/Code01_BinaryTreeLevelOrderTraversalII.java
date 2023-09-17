package com.yw.basic.course.class07;

import com.yw.entity.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 测试链接：https://leetcode.com/problems/binary-tree-level-order-traversal-ii
 *
 * @author yangwei
 */
public class Code01_BinaryTreeLevelOrderTraversalII {
    /**
     * 方法一：基于递归实现层序遍历
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        levelOrder(root, 0, ans);
        int size = ans.size();
        for (int i = 0, j = size - 1; i < j; i++, j--) {
            List<Integer> tmp = ans.get(i);
            ans.set(i, ans.get(j));
            ans.set(j, tmp);
        }
        return ans;
    }

    private void levelOrder(TreeNode root, int level, List<List<Integer>> ans) {
        if (root == null) return;
        if (level == ans.size()) ans.add(new ArrayList<>());
        ans.get(level).add(root.val);
        levelOrder(root.left, level + 1, ans);
        levelOrder(root.right, level + 1, ans);
    }

    /**
     * 方法二：基于队列实现层序遍历
     */
    public List<List<Integer>> levelOrderBottom2(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            ans.add(0, list);
        }
        return ans;
    }
}
