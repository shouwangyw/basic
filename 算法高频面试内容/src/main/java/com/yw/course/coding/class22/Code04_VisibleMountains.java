package com.yw.course.coding.class22;

import java.util.HashSet;
import java.util.Stack;

import static com.yw.course.coding.class24.Code02_KthMinPair.getRandomArray;
import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code04_VisibleMountains {

    // 获取可见山峰对数量
    public static int getVisibleCount(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        // maxIdx: 最大值所在的位置
        int n = arr.length, maxIdx = 0;
        for (int i = 0; i < n; i++) {
            maxIdx = arr[maxIdx] < arr[i] ? i : maxIdx;
        }
        Stack<Info> stack = new Stack<>();
        // 将(最大值,1)这个记录放入stack中
        stack.push(new Info(arr[maxIdx]));
        // 从最大值位置的下一个位置开始沿next方向遍历，用“小找大”的方式统计所有可见山峰对
        int idx = nextIndex(maxIdx, n), ans = 0;
        while (idx != maxIdx) {
            // 当前数要进入栈，判断会不会破坏第一维的数字从顶到底依次变大
            // 如果破坏了，就依次弹出栈顶记录，并计算山峰对数量
            while (stack.peek().val < arr[idx]) {
                int k = stack.pop().cnt;
                // 弹出记录为(x,k)，如果k==1，产生2对; 如果K>1，产生2*K + C(2,K)对
                ans += getInternalSum(k) + 2 * k;
            }
            // 当前数字arr[index]要进入栈了，如果和当前栈顶数字一样就合并
            // 不一样就把记录(arr[index],1)放入栈中
            if (stack.peek().val == arr[idx]) stack.peek().cnt++;
            else stack.push(new Info(arr[idx]));
            idx = nextIndex(idx, n);
        }
        // 清算阶段: 清算阶段的第1小阶段
        while (stack.size() > 2) {
            int k = stack.pop().cnt;
            ans += getInternalSum(k) + 2 * k;
        }
        // 清算阶段: 清算阶段的第2小阶段
        if (stack.size() == 2) {
            int k = stack.pop().cnt;
            ans += getInternalSum(k) + (stack.peek().cnt == 1 ? 1 : 2) * k;
        }
        // 清算阶段: 清算阶段的第3小阶段
        ans += getInternalSum(stack.pop().cnt);
        return ans;
    }
    private static class Info {
        private int val;    // 山峰高度值
        private int cnt;    // 值val出现的次数
        public Info(int val) {
            this.val = val;
            this.cnt = 1;
        }
    }
    // 环形数组中当前位置为i，数组长度为size，返回i的下一个位置
    private static int nextIndex(int i, int n) {
        return i < (n - 1) ? (i + 1) : 0;
    }
    // 如果k==1返回0，如果k>1返回C(2,k)
    private static int getInternalSum(int k) {
        return k == 1 ? 0 : (k * (k - 1) / 2);
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 10;
        int testTimes = 3000000;
        System.out.println("test begin!");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = getRandomArray(size, max);
            int ans1 = rightWay(arr);
            int ans2 = getVisibleCount(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("test end!");
    }
    // for test, O(N^2)的解法，绝对正确
    public static int rightWay(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int res = 0;
        HashSet<String> equalCounted = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            // 枚举从每一个位置出发，根据“小找大”原则能找到多少对儿，并且保证不重复找
            res += getVisibleNumFromIndex(arr, i, equalCounted);
        }
        return res;
    }
    // 根据“小找大”的原则返回从index出发能找到多少对
    // 相等情况下，比如arr[1]==3，arr[5]==3
    // 之前如果从位置1找过位置5，那么等到从位置5出发时就不再找位置1（去重）
    // 之前找过的、所有相等情况的山峰对，都保存在了equalCounted中
    private static int getVisibleNumFromIndex(int[] arr, int index,
                                              HashSet<String> equalCounted) {
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i != index) { // 不找自己
                if (arr[i] == arr[index]) {
                    String key = Math.min(index, i) + "_" + Math.max(index, i);
                    // 相等情况下，确保之前没找过这一对
                    if (equalCounted.add(key) && isVisible(arr, index, i)) {
                        res++;
                    }
                } else if (isVisible(arr, index, i)) { // 不相等的情况下直接找
                    res++;
                }
            }
        }
        return res;
    }
    // 调用该函数的前提是，lowIndex和highIndex一定不是同一个位置
    // 在“小找大”的策略下，从lowIndex位置能不能看到highIndex位置
    // next方向或者last方向有一个能走通，就返回true，否则返回false
    private static boolean isVisible(int[] arr, int lowIndex, int highIndex) {
        if (arr[lowIndex] > arr[highIndex]) { // “大找小”的情况直接返回false
            return false;
        }
        int size = arr.length;
        boolean walkNext = true;
        int mid = nextIndex(lowIndex, size);
        // lowIndex通过next方向走到highIndex，沿途不能出现比arr[lowIndex]大的数
        while (mid != highIndex) {
            if (arr[mid] > arr[lowIndex]) {
                walkNext = false;// next方向失败
                break;
            }
            mid = nextIndex(mid, size);
        }
        boolean walkLast = true;
        mid = lastIndex(lowIndex, size);
        // lowIndex通过last方向走到highIndex，沿途不能出现比arr[lowIndex]大的数
        while (mid != highIndex) {
            if (arr[mid] > arr[lowIndex]) {
                walkLast = false; // last方向失败
                break;
            }
            mid = lastIndex(mid, size);
        }
        return walkNext || walkLast; // 有一个成功就是能相互看见
    }
    // 环形数组中当前位置为i，数组长度为size，返回i的上一个位置
    private static int lastIndex(int i, int size) {
        return i > 0 ? (i - 1) : (size - 1);
    }
}