package com.yw.basic.course.class07;

import com.yw.entity.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试链接：https://leetcode.cn/problems/path-sum-ii
 *
 * @author yangwei
 */
public class Code04_PathSumII {

    /**
     * 方法一
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        findPathSum(root, targetSum, path, ans);
        return ans;
    }

    private void findPathSum(TreeNode root, int targetSum, List<Integer> path, List<List<Integer>> ans) {
        if (root == null) return;
        path.add(root.val);
        if (root.left == null && root.right == null && root.val == targetSum) {
            ans.add(new ArrayList<>(path));
            path.remove(path.size() - 1); // 回溯-恢复
            return;
        }
        if (root.left != null) findPathSum(root.left, targetSum - root.val, path, ans);
        if (root.right != null) findPathSum(root.right, targetSum - root.val, path, ans);
        path.remove(path.size() - 1); // 回溯-恢复
    }

    /**
     * 方法二
     */
    public static List<List<Integer>> pathSum2(TreeNode root, int sum) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        ArrayList<Integer> path = new ArrayList<>();
        process(root, path, 0, sum, ans);
        return ans;
    }

    public static void process(TreeNode x, List<Integer> path, int preSum, int sum, List<List<Integer>> ans) {
        if (x.left == null && x.right == null) {
            if (preSum + x.val == sum) {
                path.add(x.val);
                ans.add(copy(path));
                path.remove(path.size() - 1);
            }
            return;
        }
        // x 非叶节点
        path.add(x.val);
        preSum += x.val;
        if (x.left != null) {
            process(x.left, path, preSum, sum, ans);
        }
        if (x.right != null) {
            process(x.right, path, preSum, sum, ans);
        }
        path.remove(path.size() - 1);
    }

    public static List<Integer> copy(List<Integer> path) {
        List<Integer> ans = new ArrayList<>();
        for (Integer num : path) {
            ans.add(num);
        }
        return ans;
    }

}
