package com.yw.course.coding.class18;

import java.util.*;

/**
 * 牛客的测试链接：
 * https://www.nowcoder.com/practice/7201cacf73e7495aa5f88b223bbbf6d1
 * 不要提交包信息，把import底下的类名改成Main，提交下面的代码可以直接通过
 * 因为测试平台会卡空间，所以把set换成了动态加和减的结构
 *
 * @author yangwei
 */
public class Code04_TopKSumCrossTwoArrays {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), k = in.nextInt();
        int[] arr1 = new int[n], arr2 = new int[n];
        for (int i = 0; i < n; i++) arr1[i] = in.nextInt();
        for (int i = 0; i < n; i++) arr2[i] = in.nextInt();
        int[] topK = topKsum(arr1, arr2, Math.min(k, 2 * n));
        for (int x : topK) System.out.print(x + " ");
        System.out.println();
        in.close();
    }

    private static int[] topKsum(int[] arr1, int[] arr2, int k) {
        int[] ans = new int[k];
        Queue<Node> maxHeap = new PriorityQueue<>((o1, o2) -> o2.sum - o1.sum);
        Set<String> visited = new HashSet<>();
        int i = arr1.length - 1, j = arr2.length - 1, idx = 0;
        maxHeap.offer(new Node(i, j, arr1[i] + arr2[j]));
        visited.add(pos(i, j));
        while (idx < k) {
            Node cur = maxHeap.poll();
            ans[idx++] = cur.sum;
            i = cur.i;
            j = cur.j;
            if (i - 1 >= 0 && !visited.contains(pos(i - 1, j))) {
                maxHeap.add(new Node(i - 1, j, arr1[i - 1] + arr2[j]));
                visited.add(pos(i - 1, j));
            }
            if (j - 1 >= 0 && !visited.contains(pos(i, j - 1))) {
                maxHeap.add(new Node(i, j - 1, arr1[i] + arr2[j - 1]));
                visited.add(pos(i, j - 1));
            }
        }
        return ans;
    }

    private static class Node {
        private int i;   // arr1中的位置
        private int j;   // arr2中的位置
        private int sum; // arr[i] + arr[j]的值

        public Node(int i, int j, int sum) {
            this.i = i;
            this.j = j;
            this.sum = sum;
        }
    }

    private static String pos(int i, int j) {
        return String.format("%d_%d", i, j);
    }
}
