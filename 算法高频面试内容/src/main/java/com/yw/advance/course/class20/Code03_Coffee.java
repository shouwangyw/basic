package com.yw.advance.course.class20;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 给定一个数组arr，arr[i] 代表第 i 号咖啡机泡一杯咖啡的时间。
 * 给定一个正数N，表示 N 个人等着咖啡机泡咖啡，每台咖啡机只能轮流泡咖啡。
 * 只有一台咖啡机，一次只能洗一个杯子，时间耗费a，洗完才能洗下一杯。
 * 每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发。
 * 假设所有人拿到咖啡之后立刻喝干净，返回从开始等到所有咖啡机变干净的最短时间。
 * 三个参数：int[] arr、int n，int a、int b。
 *
 * @author yangwei
 */
public class Code03_Coffee {

    // 验证的方法
    // 彻底的暴力
    // 很慢但是绝对正确
    public static int right(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }

    // 每个人暴力尝试用每一个咖啡机给自己做咖啡
    public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }

    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);
    }

    // 方法一：贪心+优良暴力尝试
    private static class Machine {
        int timePoint;  // 可用时间点
        int workTime;   // 泡咖啡时间

        public Machine(int t, int w) {
            timePoint = t;
            workTime = w;
        }
    }
    public static int minTime0(int[] arr, int n, int a, int b) {
        Queue<Machine> heap = new PriorityQueue<>(Comparator.comparingInt(o -> (o.timePoint + o.workTime)));
        // 0时间点都可用
        for (int value : arr) heap.add(new Machine(0, value));
        // 每个人喝完咖啡的最优时间
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        return bestTime(drinks, a, b, 0, 0);
    }
    /**
     * @param drinks 所有杯子可以开始洗的时间
     * @param wash   单杯洗干净的时间（串行）
     * @param air    挥发干净的时间(并行)
     * @param idx    第几号杯子
     * @param free   洗机器什么时候可用
     * @return 都变干净，最早的结束时间
     */
    private static int bestTime(int[] drinks, int wash, int air, int idx, int free) {
        if (idx == drinks.length) return 0;
        // idx号杯子 决定洗
        int selfClean1 = Math.max(drinks[idx], free) + wash;
        int restClean1 = bestTime(drinks, wash, air, idx + 1, selfClean1);
        int p1 = Math.max(selfClean1, restClean1);

        // idx号杯子 决定挥发
        int selfClean2 = drinks[idx] + air;
        int restClean2 = bestTime(drinks, wash, air, idx + 1, free);
        int p2 = Math.max(selfClean2, restClean2);
        return Math.min(p1, p2);
    }

    // 方法二：改成动态规划
    public static int minTime(int[] arr, int n, int a, int b) {
        Queue<Machine> heap = new PriorityQueue<>(Comparator.comparingInt(o -> (o.timePoint + o.workTime)));
        for (int value : arr) heap.add(new Machine(0, value));
        // 每个人喝完咖啡的最优时间
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        return bestTimeByDp(drinks, a, b);
    }
    private static int bestTimeByDp(int[] drinks, int wash, int air) {
        int n = drinks.length, maxFree = 0;
        for (int i = 0; i < n; i++) {
            maxFree = Math.max(maxFree, drinks[i]) + wash;
        }
        int[][] dp = new int[n + 1][maxFree + 1];
        for (int idx = n - 1; idx >= 0; idx--) {
            for (int free = 0; free <= maxFree; free++) {
                int selfClean1 = Math.max(drinks[idx], free) + wash;
                if (selfClean1 > maxFree) break;    // 因为后面的也都不用填了
                // idx号杯子 决定洗
                int restClean1 = dp[idx + 1][selfClean1];
                int p1 = Math.max(selfClean1, restClean1);
                // idx号杯子 决定挥发
                int selfClean2 = drinks[idx] + air;
                int restClean2 = dp[idx + 1][free];
                int p2 = Math.max(selfClean2, restClean2);
                dp[idx][free] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
            int ans2 = minTime0(arr, n, a, b);
            int ans3 = minTime(arr, n, a, b);
            if (ans1 != ans2 || ans2 != ans3) {
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2 + " , " + ans3);
                System.out.println("===============");
                break;
            }
        }
        System.out.println("测试结束");

    }

    // for test
    private static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    // for test
    private static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }
}
