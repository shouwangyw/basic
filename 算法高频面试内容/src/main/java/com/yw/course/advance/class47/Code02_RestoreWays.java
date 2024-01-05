package com.yw.course.advance.class47;


/**
 * 整型数组arr长度为n(3 <= n <= 10^4)，最初每个数字是<=200的正数且满足如下条件：
 * 1. 0位置的要求：arr[0]<=arr[1]
 * 2. n-1位置的要求：arr[n-1]<=arr[n-2]
 * 3. 中间i位置的要求：arr[i]<=max(arr[i-1],arr[i+1])
 * 但是在arr有些数字丢失了，比如k位置的数字之前是正数，丢失之后k位置的数字为0
 * 请你根据上述条件，计算可能有多少种不同的arr可以满足以上条件
 * 比如 [6,0,9] 只有还原成 [6,9,9]满足全部三个条件，所以返回1种，即[6,9,9]达标
 *
 * @author yangwei
 */
public class Code02_RestoreWays {

    // 方法一：最最暴力的解法
    public static int restoreWays0(int[] arr) {
        return process0(arr, 0);
    }
    private static int process0(int[] arr, int idx) {
        if (idx == arr.length) return isValid(arr) ? 1 : 0;
        if (arr[idx] != 0) return process0(arr, idx + 1);
        int ways = 0;
        for (int v = 1; v <= 200; v++) {
            arr[idx] = v;
            ways += process0(arr, idx + 1);
        }
        arr[idx] = 0;
        return ways;
    }
    // 根据题意进行校验
    private static boolean isValid(int[] arr) {
        if (arr[0] > arr[1]) return false;
        if (arr[arr.length - 1] > arr[arr.length - 2]) return false;
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] > Math.max(arr[i - 1], arr[i + 1])) return false;
        }
        return true;
    }

    // 方法二：优良的暴力尝试
    public static int restoreWays(int[] arr) {
        int n = arr.length;
        if (arr[n - 1] != 0) return process(arr, n - 1, arr[n - 1], 2);
        int ways = 0;
        for (int v = 1; v <= 200; v++) ways += process(arr, n - 1, v, 2);
        return ways;
    }
    // 如果i位置的数变成v，并且arr[i]与arr[i+1]的关系是s
    // 0: 表示arr[i]<arr[i+1], 1表示arr[i]==arr[i+1], 2表示arr[i]>arr[i+1])
    // 返回[0,i]范围上还有多少种有效的转化方式
    private static int process(int[] arr, int i, int v, int s) {
        // base case: [0, i]范围只剩一个数
        if (i == 0) return ((s == 0 || s == 1) && (arr[0] == 0 || v == arr[0])) ? 1 : 0;
        // i>0
        if (arr[i] != 0 && v != arr[i]) return 0;
        // i<0 && i位置的数真的可以变成v
        int ways = 0;
        if (s == 0 || s == 1) {
            // [i]位置数变成v，v <= arr[i+1]
            for (int pre = 1; pre <= 200; pre++) {
                ways += process(arr, i - 1, pre, pre < v ? 0 : (pre == v ? 1 : 2));
            }
        } else {
            // 当前数>右边数，同时要当前<=max{左，右}
            for (int pre = v; pre <= 200; pre++) {
                ways += process(arr, i - 1, pre, pre == v ? 1 : 2);
            }
        }
        return ways;
    }

    // 方法三：基于尝试改动态规划
    public static int restoreWaysDp(int[] arr) {
        int n = arr.length, vm = 200;
        int[][][] dp = new int[n][vm + 1][3];
        // 初始化dp表
        // 1. arr[0]为0说明数字丢了，有解的情况: 0位置数可以填1~200 且 [0位置数]<=[1位置数]
        if (arr[0] == 0) {
            for (int v = 1; v <= vm; v++) {
                dp[0][v][0] = 1;
                dp[0][v][1] = 1;
            }
        } else {
            // 2. arr[0]不为0说明数字没丢，有解的情况: 0位置数只能是arr[0]，[0位置数]<=[1位置数]
            dp[0][arr[0]][0] = 1;
            dp[0][arr[0]][1] = 1;
        }
        // 普遍位置
        for (int i = 1; i < n; i++) {
            for (int v = 1; v <= vm; v++) {
                for (int s = 0; s <= 2; s++) {
                    // i位置没丢 且修改值v还不等于[i位置数] -- 无效
                    if (arr[i] != 0 && v != arr[i]) continue;
                    // [i位置数]<=[i+1位置数]，则i前一个数需要<=[i位置数]
                    if (s == 0 || s == 1) {
                        for (int pre = 1; pre < v; pre++) {
                            dp[i][v][s] += dp[i - 1][pre][0];
                        }
                    }
                    dp[i][v][s] += dp[i - 1][v][1];
                    // [i位置数]>[i+1位置数]，则i前一个数必需>=[i位置数]且小于等于200
                    for (int pre = v + 1; pre <= vm; pre++) {
                        dp[i][v][s] += dp[i - 1][pre][2];
                    }
                }
            }
        }
        if (arr[n - 1] != 0) return dp[n - 1][arr[n - 1]][2];
        int ways = 0;
        for (int v = 1; v <= vm; v++) ways += dp[n - 1][v][2];
        return ways;
    }

    // 方法四：动态规划-优化版本
    public static int restoreWaysDpOptimal(int[] arr) {
        int n = arr.length, vm = 200;
        int[][][] dp = new int[n][vm + 1][3];
        // 初始化dp表
        // 1. arr[0]为0说明数字丢了，有解的情况: 0位置数可以填1~200 且 [0位置数]<=[1位置数]
        if (arr[0] == 0) {
            for (int v = 1; v <= vm; v++) {
                dp[0][v][0] = 1;
                dp[0][v][1] = 1;
            }
        } else {
            // 2. arr[0]不为0说明数字没丢，有解的情况: 0位置数只能是arr[0]，[0位置数]<=[1位置数]
            dp[0][arr[0]][0] = 1;
            dp[0][arr[0]][1] = 1;
        }
        // 做一个预处理结构，二维前缀和数组
        int[][] prefixSum = new int[vm + 1][3];
        for (int v = 1; v <= vm; v++) {
            for (int s = 0; s <= 2; s++) {
                prefixSum[v][s] = prefixSum[v - 1][s] + dp[0][v][s];
            }
        }
        // 普遍位置
        for (int i = 1; i < n; i++) {
            for (int v = 1; v <= vm; v++) {
                for (int s = 0; s <= 2; s++) {
                    // i位置没丢 且修改值v还不等于[i位置数] -- 无效
                    if (arr[i] != 0 && v != arr[i]) continue;
                    // !!! 用前缀和数组优化计算区间和
                    // [i位置数]<=[i+1位置数]，则i前一个数需要<=[i位置数]
                    if (s == 0 || s == 1) dp[i][v][s] += rangeSum(1, v - 1, 0, prefixSum);
                    dp[i][v][s] += dp[i - 1][v][1];
                    // [i位置数]>[i+1位置数]，则i前一个数必需>=[i位置数]且小于等于200
                    dp[i][v][s] += rangeSum(v + 1, vm, 2, prefixSum);
                }
            }
            // 预处理下个二维前缀和数组
            for (int v = 1; v <= vm; v++) {
                for (int s = 0; s <= 2; s++) {
                    prefixSum[v][s] = prefixSum[v - 1][s] + dp[i][v][s];
                }
            }
        }
        if (arr[n - 1] != 0) return dp[n - 1][arr[n - 1]][2];
        return rangeSum(1, vm, 2, prefixSum);
    }
    private static int rangeSum(int l, int r, int s, int[][] prefixSum) {
        return prefixSum[r][s] - prefixSum[l - 1][s];
    }

    public static void main(String[] args) {
        int len = 4;
        int testTime = 15;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * len) + 2;
            int[] arr = generateRandomArray(N);
            int ans0 = restoreWays0(arr);
            int ans1 = restoreWays(arr);
            int ans2 = restoreWaysDp(arr);
            int ans3 = restoreWaysDpOptimal(arr);
            if (ans0 != ans1 || ans2 != ans3 || ans0 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("功能测试结束");
        System.out.println("===========");
        int N = 100000;
        int[] arr = generateRandomArray(N);
        long begin = System.currentTimeMillis();
        restoreWaysDpOptimal(arr);
        long end = System.currentTimeMillis();
        System.out.println("run time : " + (end - begin) + " ms");
    }

    private static int[] generateRandomArray(int len) {
        int[] ans = new int[len];
        for (int i = 0; i < ans.length; i++) {
            if (Math.random() < 0.5) {
                ans[i] = 0;
            } else {
                ans[i] = (int) (Math.random() * 200) + 1;
            }
        }
        return ans;
    }

}
