package com.yw.util;

import com.yw.entity.DoubleNode;
import com.yw.entity.Node;
import com.yw.entity.TreeNode;

import java.util.*;

/**
 * @author yangwei
 */
public class CommonUtils {
    private CommonUtils() {
        throw new RuntimeException("new CommonUtils() Unsupported");
    }

    // generate random num: [1,max]
    public static int random1ToMax(int max) {
        return (int) (Math.random() * max) + 1;
    }

    // generate random num: [-range, +range]
    public static int randomRange(int range) {
        int x = (int) (Math.random() * (range + 1));
        int y = (int) (Math.random() * (range + 1));
        return x - y;
    }

    // generate maxKinds random num, maxKinds in [-range, range]
    // and has one num appear k times，others appear m times
    public static int[] randomArrayOnlyOneKTimes(int maxKinds, int range, int k, int m) {
        // 出现k次的数
        int kTimesNum = randomRange(range);
        // 真命天子出现的次数
//        int times = k;
        // 按 50% 概率返回不出现k次的
        int times = Math.random() < 0.5 ? k : ((int) (Math.random() * (m - 1)) + 1);
        // 数组中共有多少种树，至少 2 种
        int numKinds = (int) (Math.random() * maxKinds) + 2;
        // k * 1 + (numKinds - 1) * m
        int[] arr = new int[times + (numKinds - 1) * m];
        int index = 0;
        for (; index < times; index++) {
            arr[index] = kTimesNum;
        }
        numKinds--;
        Set<Integer> set = new HashSet<>();
        set.add(kTimesNum);
        while (numKinds != 0) {
            int curNum = 0;
            do {
                curNum = randomRange(range);
            } while (set.contains(curNum));
            set.add(curNum);
            numKinds--;
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }
        }
        // arr 填好了，数据打散
        for (int i = 0; i < arr.length; i++) {
            // i 位置的数，我想随机和j位置的数做交换
            int j = (int) (Math.random() * arr.length);
            swap(arr, i, j);
        }
        return arr;
    }

    public static void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
//
//        arr[i] = arr[i] ^ arr[j];
//        arr[j] = arr[i] ^ arr[j];
//        arr[i] = arr[i] ^ arr[j];
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        // Math.random() -> [0,1) 所有的小数，等概率返回一个
        // Math.random() * N -> [0,N) 所有的小数，等概率返回一个
        // (int)(Math.random() * N) -> [0,N - 1] 所有的整数，等概率返回一个
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] copy = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1 == null || arr2 == null || arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void printArray(boolean[] arr) {
        if (arr == null) {
            return;
        }
        System.out.println(Arrays.toString(arr));
    }

    public static Node generateRandomLinkedList(int len, int value) {
        int size = (int) (Math.random() * (len + 1));
        if (size == 0) return null;
        size--;
        Node head = new Node((int) (Math.random() * (value + 1)));
        Node cur = head;
        while (size-- > 0) {
            Node node = new Node((int) (Math.random() * (value + 1)));
            cur.next = node;
            cur = node;
        }
        return head;
    }

    public static DoubleNode generateRandomDoubleList(int len, int value) {
        int size = (int) (Math.random() * (len + 1));
        if (size == 0) return null;
        size--;
        DoubleNode head = new DoubleNode((int) (Math.random() * (value + 1)));
        DoubleNode cur = head;
        while (size-- > 0) {
            DoubleNode node = new DoubleNode((int) (Math.random() * (value + 1)));
            cur.next = node;
            node.prev = cur;
            cur = node;
        }
        return head;
    }

    public static List<Integer> getLinkedListValues(Node head) {
        List<Integer> ans = new ArrayList<>();
        while (head != null) {
            ans.add(head.val);
            head = head.next;
        }
        return ans;
    }

    public static void printLinkedListValues(Node head) {
        while (head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
        System.out.println();
    }

    public static List<Integer> getDoubleListValues(DoubleNode head) {
        List<Integer> ans = new ArrayList<>();
        while (head != null) {
            ans.add(head.val);
            head = head.next;
        }
        return ans;
    }

    public static void printDoubleListValues(DoubleNode head) {
        while (head != null) {
            System.out.print("<-" + head.val + (head.next == null ? "->" : "<->"));
            head = head.next;
        }
        System.out.println();
    }

    public static TreeNode generateRandomBST(int maxLevel, int maxVal) {
        return generate(1, maxLevel, maxVal);
    }

    public static TreeNode generate(int level, int maxLevel, int maxVal) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode root = new TreeNode((int) (Math.random() * maxVal));
        root.left = generate(level + 1, maxLevel, maxVal);
        root.right = generate(level + 1, maxLevel, maxVal);
        return root;
    }

    public static boolean isSameValStructure(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 != null) {
            return false;
        }
        if (root1 != null && root2 == null) {
            return false;
        }
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1.val != root2.val) {
            return false;
        }
        return isSameValStructure(root1.left, root2.left) && isSameValStructure(root1.right, root2.right);
    }

    public static void printTree(TreeNode root) {
        System.out.println("Binary Tree:");
        printInOrder(root, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(TreeNode root, int height, String to, int len) {
        if (root == null) {
            return;
        }
        printInOrder(root.right, height + 1, "v", len);
        String val = to + root.val + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(root.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    public static TreeNode generateTreeNode(String val) {
        if (val == null) {
            return null;
        }
        return new TreeNode(Integer.valueOf(val));
    }

    public static TreeNode pickRandomOne(TreeNode head) {
        if (head == null) {
            return null;
        }
        ArrayList<TreeNode> arr = new ArrayList<>();
        fillPreList(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    private static void fillPreList(TreeNode head, ArrayList<TreeNode> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPreList(head.left, arr);
        fillPreList(head.right, arr);
    }

    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    private static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5);
            ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }
}
