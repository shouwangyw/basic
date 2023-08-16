package com.yw.basic.course.class01;

/**
 * 给定一个参数 N
 * 返回：1! +  2! + 3! + 4! + ... + N! 的结果
 *
 * @author yangwei
 */
public class Code01_SumOfFactorial {
    private static long f1(int n) {
        long ans = 0;
        for (int i = 1; i <= n; i++) {
            ans += factorial(i);
        }
        return ans;
    }

    private static long factorial(int n) {
        long ans = 1;
        for (int i = 1; i <= n; i++) {
            ans *= i;
        }
        return ans;
    }

    private static long f2(int n) {
        long ans = 0;
        long cur = 1;
        for (int i = 1; i <= n; i++) {
            cur *= i;
            ans += cur;
        }
        return ans;
    }

    public static void main(String[] args) {
        int N = 10;
        System.out.println(f1(N));
        System.out.println(f2(N));
    }
}
