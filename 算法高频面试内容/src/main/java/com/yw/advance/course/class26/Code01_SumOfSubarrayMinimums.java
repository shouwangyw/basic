package com.yw.advance.course.class26;

import java.util.Arrays;

import static com.yw.util.CommonUtils.printArray;
import static com.yw.util.CommonUtils.randomArray;

/**
 * 测试链接：https://leetcode.cn/problems/sum-of-subarray-minimums/
 *
 * @author yangwei
 */
public class Code01_SumOfSubarrayMinimums {
    // Leetcode上不要提交subArrayMinSum1、subArrayMinSum2方法，因为没有考虑取摸
    // Leetcode上只提交sumSubarrayMins方法，时间复杂度O(N)，可以直接通过

    // 方法一: 暴力解
    public static int subArrayMinSum1(int[] arr) {
        int n = arr.length, ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                // 枚举每一个子数组，求子数组的最小值
                int min = arr[i];
                for (int k = i + 1; k <= j; k++) {
                    min = Math.min(min, arr[k]);
                }
                ans += min;
            }
        }
        return ans;
    }

    // 方法二：没有用单调栈的最优解
    public static int subArrayMinSum2(int[] arr) {
        int n = arr.length;
        long ans = 0;
        // 遍历arr中每一个数，分别找到左边最近小于等于、右边最近小于位置
        for (int i = 0; i < n; i++) {
            // l: arr[i]左边、离arr[i]最近且 <= arr[i] 的位置
            // r: arr[i]右边、离arr[i]最近且 < arr[i] 的位置
            int l = -1, r = n;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] <= arr[i]) {
                    l = j;
                    break;
                }
            }
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[i]) {
                    r = j;
                    break;
                }
            }
            // 整理答案
            ans += (i - l) * (r - i) * (long) arr[i];
            ans %= 1000000007;
        }
        return (int) ans;
    }

    // 方法三：使用单调栈优化的最优解
    public static int sumSubarrayMins0(int[] arr) {
        int n = arr.length, si = -1;
        int[] stack = new int[n];
        // left[i]: arr[i]左边、离arr[i]最近且 <= arr[i] 的位置
        // right[i]: arr[i]右边、离arr[i]最近且 < arr[i] 的位置
        int[] left = new int[n], right = new int[n];
        // 初始化nearLess
        Arrays.fill(left, -1);
        Arrays.fill(right, n);
        long ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            while (si != -1 && arr[stack[si]] >= arr[i]) left[stack[si--]] = i;
            stack[++si] = i;
        }
        si = -1;
        for (int i = 0; i < n; i++) {
            while (si != -1 && arr[stack[si]] > arr[i]) right[stack[si--]] = i;
            stack[++si] = i;
        }
        // 整理答案
        for (int i = 0; i < n; i++) {
            ans += (i - left[i]) * (right[i] - i) * (long) arr[i];
            ans %= 1000000007;
        }
        return (int) ans;
    }

    // 方法四：数组单调栈+动态规划
    public static int sumSubarrayMins(int[] arr) {
        int n = arr.length, si = -1;
        int[] stack = new int[n];
        long ans = 0;
        long[] sums = new long[n + 1];
        for (int i = 0; i < n; i++) {
            while (si != -1 && arr[stack[si]] >= arr[i]) si--;
            int idx = si == -1 ? -1 : stack[si];
            stack[++si] = i;
            sums[si + 1] = sums[si] + arr[i] * (i - idx);
            ans += sums[si + 1];
        }
        return (int) (ans % 1000000007);
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 50;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = subArrayMinSum1(arr);
            int ans2 = subArrayMinSum2(arr);
            int ans3 = sumSubarrayMins(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
