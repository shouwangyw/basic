package com.yw.course.coding.class19;

/**
 * @author yangwei
 */
public class Code03_OneNumber {

    // 方法一：暴力解
    public static int count1char0(int num) {
        if (num < 1) return 0;
        int cnt = 0;
        // 遍历每一位数字，收集所有1字符出现的次数
        for (int i = 1; i <= num; i++) {
            cnt += getCnt(i);
        }
        return cnt;
    }
    private static int getCnt(int num) {
        int cnt = 0;
        while (num != 0) {
            if (num % 10 == 1) cnt++;
            num /= 10;
        }
        return cnt;
    }

    // 方法二：数位DP
    public static int count1char(int num) {
        if (num < 1) return 0;
        // num = 13625, len = 5位数
        int len = getLenOfNum(num);
        if (len == 1) return 1;
        // 最高位最大贡献1的次数：num  7872328738273 -> 1000000000000
        int firstMax = (int) Math.pow(10, len - 1);
        // 最高位
        int first = num / firstMax;
        // 最高位贡献1的次数
        int firstCnt = first == 1 ? num % firstMax + 1 : firstMax;
        // 除去最高位之外，剩下位数贡献1的数量
        // 最高位是1: 10^(k-2) * (k-1) * 1
        // 最高位是first: 10^(k-2) * (k-1) * first
        int otherCnt = first * (len - 1) * (firstMax / 10);
        return firstCnt + otherCnt + count1char(num % firstMax);
    }
    private static int getLenOfNum(int num) {
        int len = 0;
        while (num != 0) {
            len++;
            num /= 10;
        }
        return len;
    }
    public static void main(String[] args) {
        int num = 50000000;
        long start1 = System.currentTimeMillis();
        System.out.println(count1char0(num));
        long end1 = System.currentTimeMillis();
        System.out.println("cost time: " + (end1 - start1) + " ms");

        long start2 = System.currentTimeMillis();
        System.out.println(count1char(num));
        long end2 = System.currentTimeMillis();
        System.out.println("cost time: " + (end2 - start2) + " ms");
    }
}
