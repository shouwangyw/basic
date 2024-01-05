package com.yw.course.advance.class11;

import com.yw.entity.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

import static com.yw.util.CommonUtils.*;

public class Code02_SerializeAndReconstructTree {
    /*
     * 二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，
     * 以下代码全部实现了。
     * 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
     * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
     * 比如如下两棵树
     *         __2
     *        /
     *       1
     *       和
     *       1__
     *          \
     *           2
     * 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
     *
     * */
    // 先序序列化
    public static List<Integer> serialByPreOrder(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        preOrder(root, result);
        return result;
    }
    private static void preOrder(TreeNode root, List<Integer> result) {
        if (root == null) result.add(null);
        else {
            result.add(root.val);
            preOrder(root.left, result);
            preOrder(root.right, result);
        }
    }
    // 先序反序列化
    public static TreeNode deserialByPreOrder(List<Integer> preOrderList) {
        if (preOrderList == null || preOrderList.size() == 0) return null;

        return preOrder(preOrderList);
    }
    private static TreeNode preOrder(List<Integer> preOrderList) {
        Integer val = preOrderList.remove(0);
        if (val == null) return null;
        TreeNode root = new TreeNode(val);
        root.left = preOrder(preOrderList);
        root.right = preOrder(preOrderList);
        return root;
    }

//    public static Queue<String> inSerial(TreeNode root) {
//        Queue<String> ans = new LinkedList<>();
//        ins(root, ans);
//        return ans;
//    }

//    public static void ins(TreeNode root, Queue<String> ans) {
//        if (root == null) {
//            ans.add(null);
//        } else {
//            ins(root.left, ans);
//            ans.add(String.valueOf(root.val));
//            ins(root.right, ans);
//        }
//    }

    // 后序序列化
    public static List<Integer> serialByPostOrder(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        postOrder(root, result);
        return result;
    }
    private static void postOrder(TreeNode root, List<Integer> result) {
        if (root == null) result.add(null);
        else {
            postOrder(root.left, result);
            postOrder(root.right, result);
            result.add(root.val);
        }
    }
    // 后序反序列化
    public static TreeNode deserialByPostOrder(List<Integer> postOrderList) {
        if (postOrderList == null || postOrderList.size() == 0) return null;

        return postOrder(postOrderList);
    }
    private static TreeNode postOrder(List<Integer> postOrderList) {
        // postOrderList 从左往右是 `左右根`，反过来就是 `根右左`
        Integer val = postOrderList.remove(postOrderList.size() - 1);
        if (val == null) return null;
        TreeNode root = new TreeNode(val);
        root.right = postOrder(postOrderList);
        root.left = postOrder(postOrderList);
        return root;
    }

    // 层序序列化
    public static List<Integer> serialByLevelOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        levelOrder(root, 0, result);
        return result.stream().filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
    }
    private static void levelOrder(TreeNode root, int level, List<List<Integer>> result) {
        if (level == result.size()) result.add(new LinkedList<>());
        if (root == null) result.get(level).add(null);
        else {
            result.get(level).add(root.val);
            levelOrder(root.left, level + 1, result);
            levelOrder(root.right, level + 1, result);
        }
    }
    // 层序反序列化
    public static TreeNode deserialBylevelOrder(List<Integer> levelOrderList) {
        if (levelOrderList == null || levelOrderList.size() == 0) return null;
        TreeNode root = generateTreeNode(levelOrderList.remove(0));
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            node.left = generateTreeNode(levelOrderList.remove(0));
            node.right = generateTreeNode(levelOrderList.remove(0));
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        return root;
    }
    public static TreeNode generateTreeNode(Integer val) {
        if (val == null) return null;
        return new TreeNode(val);
    }
//    public static Queue<String> levelSerial(TreeNode root) {
//        Queue<String> ans = new LinkedList<>();
//        if (root == null) {
//            ans.add(null);
//        } else {
//            ans.add(String.valueOf(root.val));
//            Queue<TreeNode> queue = new LinkedList<>();
//            queue.add(root);
//            while (!queue.isEmpty()) {
//                root = queue.poll(); // root 父   子
//                if (root.left != null) {
//                    ans.add(String.valueOf(root.left.val));
//                    queue.add(root.left);
//                } else {
//                    ans.add(null);
//                }
//                if (root.right != null) {
//                    ans.add(String.valueOf(root.right.val));
//                    queue.add(root.right);
//                } else {
//                    ans.add(null);
//                }
//            }
//        }
//        return ans;
//    }
//
//    public static TreeNode buildByLevelQueue(Queue<String> levelList) {
//        if (levelList == null || levelList.size() == 0) {
//            return null;
//        }
//        TreeNode root = generateTreeNode(levelList.poll());
//        Queue<TreeNode> queue = new LinkedList<>();
//        if (root != null) {
//            queue.add(root);
//        }
//        TreeNode TreeNode = null;
//        while (!queue.isEmpty()) {
//            TreeNode = queue.poll();
//            TreeNode.left = generateTreeNode(levelList.poll());
//            TreeNode.right = generateTreeNode(levelList.poll());
//            if (TreeNode.left != null) {
//                queue.add(TreeNode.left);
//            }
//            if (TreeNode.right != null) {
//                queue.add(TreeNode.right);
//            }
//        }
//        return root;
//    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxVal = 100;
//        int testTimes = 1;
        int testTimes = 1000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            TreeNode root = generateRandomBST(maxLevel, maxVal);

            // 序列化
            List<Integer> preOrderList = serialByPreOrder(root);
            List<Integer> postOrderList = serialByPostOrder(root);
            List<Integer> levelOrderList = serialByLevelOrder(root);
            // 反序列化
            TreeNode buildTreeByPreOrder = deserialByPreOrder(preOrderList);
            TreeNode buildTreeByPostOrder = deserialByPostOrder(postOrderList);
            TreeNode buildTreeByLevelOrder = deserialBylevelOrder(levelOrderList);
            if (!isSameValStructure(buildTreeByPreOrder, buildTreeByPostOrder) || !isSameValStructure(buildTreeByPostOrder, buildTreeByLevelOrder)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }
}
