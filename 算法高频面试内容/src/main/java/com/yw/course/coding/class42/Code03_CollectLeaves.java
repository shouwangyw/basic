package com.yw.course.coding.class42;

import com.yw.entity.TreeNode;

import java.util.*;

/**
 * 一颗有很多节点的二叉树需要收集所有节点，具体的收集规则如下：
 * 先收集那些为叶子的节点，收集过的节点可以认为从原树中去掉了，接着收集当前情况下为叶子的节点，
 * 照整个规则，一直收集叶子节点，直到最后收集头节点结束，最后将收集好的节点序列返回。
 * 当有多个叶子节点时，节点间的先后顺序无所谓。
 *
 * @author yangwei
 */
public class Code03_CollectLeaves {

    public static List<List<Integer>> collectLeavesInLayers(TreeNode root) {
        // 分层收集叶子节点，直至根节点
        Map<TreeNode, TreeNode> nodeParentMap = new HashMap<>();
        Map<TreeNode, Integer> nodeInDegreeMap = new HashMap<>();
        preOrder(root, nodeParentMap, nodeInDegreeMap);
        Queue<TreeNode> zeroInQueue = new LinkedList<>();
        for (Map.Entry<TreeNode, Integer> entry : nodeInDegreeMap.entrySet()) {
            if (entry.getValue() == 0) zeroInQueue.add(entry.getKey());
        }
        List<List<Integer>> ans = new LinkedList<>();
        while (!zeroInQueue.isEmpty()) {
            List<Integer> leafs = new LinkedList<>();
            int size = zeroInQueue.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = zeroInQueue.poll();
                if (cur == null) continue;
                leafs.add(cur.val);
                TreeNode parent = nodeParentMap.get(cur);
                if (parent == null) continue;
                int inDeg = nodeInDegreeMap.compute(parent, (k, v) -> v == null ? -1 : v - 1);
                if (inDeg == 0) zeroInQueue.add(parent);
            }
            ans.add(leafs);
        }
        return ans;
    }

    private static void preOrder(TreeNode root, Map<TreeNode, TreeNode> nodeParentMap,
                                 Map<TreeNode, Integer> nodeInDegreeMap) {
        if (root == null) return;
        nodeInDegreeMap.put(root, 0);
        if (root.left != null) {
            nodeParentMap.put(root.left, root);
            nodeInDegreeMap.compute(root, (k, v) -> v == null ? 1 : v + 1);
        }
        if (root.right != null) {
            nodeParentMap.put(root.right, root);
            nodeInDegreeMap.compute(root, (k, v) -> v == null ? 1 : v + 1);
        }
        preOrder(root.left, nodeParentMap, nodeInDegreeMap);
        preOrder(root.right, nodeParentMap, nodeInDegreeMap);
    }


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1,
                new TreeNode(2, new TreeNode(4, null, null), new TreeNode(5, null, null)),
                new TreeNode(3, null, new TreeNode(6, null, new TreeNode(7))));

        List<List<Integer>> ans = collectLeavesInLayers(root);

        ans.forEach(System.out::println);
    }
}
