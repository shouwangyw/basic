package com.yw.basic.course.class01;

/**
 * @author yangwei
 */
public class Code02_SumOfFactorial {
    // 方法一
    public static long f1(int n) {
        long ans = 0;
        for (int i = 1; i <= n; i++) {
            ans += factorial(i);
        }
        return ans;
    }

    public static long factorial(int n) {
        long ans = 1;
        for (int i = 1; i <= n; i++) {
            ans *= i;
        }
        return ans;
    }

    // 方法二，每次都基于上次计算结果进行计算 -> 更优
    public static long f2(int n) {
        // 第1次：1! -> cur
        // 第2次: 2! = 1!*2 -> cur*2 -> cur
        // 第3次: 3! = 2!*3 -> cur*3 -> cur
        // ...
        // 第n次: n! = (n-1)!*n -> cur*n
        long ans = 0;
        long cur = 1;
        for (int i = 1; i <= n; i++) {
            cur = cur * i;
            ans += cur;
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 10;
        System.out.println(f1(n));
        System.out.println(f2(n));
    }
}
