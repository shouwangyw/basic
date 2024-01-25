package com.yw.course.coding.class05;

import com.yw.entity.TreeNode;

import java.util.Arrays;
import java.util.Stack;

/**
 * 测试链接 : https://leetcode.cn/problems/construct-binary-search-tree-from-preorder-traversal/
 *
 * @author yangwei
 */
public class Code01_ConstructBinarySearchTreeFromPreorderTraversal {

    // 方法一：
    public TreeNode bstFromPreorder0(int[] preorder) {
        if (preorder == null || preorder.length == 0) return null;
        return process0(preorder, 0, preorder.length - 1);
    }
    // 返回 [l, r]范围构建出的二叉搜索树
    private static TreeNode process0(int[] pre, int l, int r) {
        if (l > r) return null;
        // 找到第一个比l位置数大的位置
        int firstBig = l + 1;
        for (; firstBig <= r; firstBig++) {
            if (pre[firstBig] > pre[l]) break;
        }
        TreeNode root = new TreeNode(pre[l]);
        root.left = process0(pre, l + 1, firstBig - 1);
        root.right = process0(pre, firstBig, r);
        return root;
    }

    // 方法二：利用单调栈优化，已经是时间复杂度最优的方法了，但是常数项还能优化
    public static TreeNode bstFromPreorder(int[] preorder) {
        if (preorder == null || preorder.length == 0) return null;
        int n = preorder.length;
        // 生成一个辅助数组，记录每个i位置的数，在右边大于它且离它最近的数的位置
        int[] nearBig = new int[n];
        Arrays.fill(nearBig, -1);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && preorder[stack.peek()] < preorder[i]) nearBig[stack.pop()] = i;
            stack.push(i);
        }
        return process(preorder, 0, n - 1, nearBig);
    }

    // 方法三：最优解，利用数组替换Stack
    public static TreeNode bstFromPreorderOptimal(int[] preorder) {
        if (preorder == null || preorder.length == 0) return null;
        int n = preorder.length;
        // 生成一个辅助数组，记录每个i位置的数，在右边大于它且离它最近的数的位置
        int[] nearBig = new int[n];
        Arrays.fill(nearBig, -1);
        int[] stack = new int[n];
        int size = 0;
        for (int i = 0; i < n; i++) {
            while (size != 0 && preorder[stack[size - 1]] < preorder[i]) {
                nearBig[stack[--size]] = i;
            }
            stack[size++] = i;
        }
        return process(preorder, 0, n - 1, nearBig);
    }
    private static TreeNode process(int[] pre, int l, int r, int[] nearBig) {
        if (l > r) return null;
        // 利用单调栈优化`找到第一个比l位置数大的位置`
        int firstBig = (nearBig[l] == -1 || nearBig[l] > r) ? r + 1 : nearBig[l];
        TreeNode root = new TreeNode(pre[l]);
        root.left = process(pre, l + 1, firstBig - 1, nearBig);
        root.right = process(pre, firstBig, r, nearBig);
        return root;
    }

}
