package com.yw.advance.course.class11;

import com.yw.entity.TreeNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static com.yw.util.CommonUtils.generateRandomBST;

/**
 * @author yangwei
 */
public class Code05_TreeMaxWidth {

public static int getMaxWidthUseMap(TreeNode root) {
    if (root == null) return 0;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    // 记录节点在那一层
    Map<TreeNode, Integer> levelMap = new HashMap<>();
    levelMap.put(root, 1);
    int curLevel = 1;           // 当前你正在统计哪一层的宽度
    int max = 0, curNums = 0;   // 当前层宽度目前是多少
    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        Integer level = levelMap.get(node);
        if (node.left != null) {
            queue.offer(node.left);
            levelMap.put(node.left, level + 1);
        }
        if (node.right != null) {
            queue.offer(node.right);
            levelMap.put(node.right, level + 1);
        }
        if (curLevel == level) curNums++;
        else {
            max = Math.max(max, curNums);
            curLevel++;
            curNums = 1;
        }
    }
    return Math.max(max, curNums);
}

    public static int getMaxWidth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode curEnd = root;     // 记录当前层的最右节点
        TreeNode nextEnd = null;    // 记录下一层的最右节点
        int max = 0, curNums = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.left != null) {
                queue.offer(node.left);
                nextEnd = node.left;
            }
            if (node.right != null) {
                queue.offer(node.right);
                nextEnd = node.right;
            }
            curNums++;
            if (node == curEnd) {
                max = Math.max(max, curNums);
                curNums = 0;
                curEnd = nextEnd;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode root = generateRandomBST(maxLevel, maxValue);
            if (getMaxWidthUseMap(root) != getMaxWidth(root)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
