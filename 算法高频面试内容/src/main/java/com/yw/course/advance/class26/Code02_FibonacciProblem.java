package com.yw.course.advance.class26;

/**
 * @author yangwei
 */
public class Code02_FibonacciProblem {

    // 方法一：暴力递归
    public static int f1(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return 1;
        return f1(n - 1) + f1(n - 2);
    }

    // 方法二：缓存递推，时间复杂度 O(N)
    public static int f2(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return 1;
        int ans = 1, pre = 1, tmp = 0;
        for (int i = 3; i <= n; i++) {
            tmp = ans;
            ans += pre;
            pre = tmp;
        }
        return ans;
    }

    // 方法三：时间复杂度 O(logN)
    public static int f3(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return 1;
        // |1 1|
        // |1 0|
        int[][] base = {{1, 1}, {1, 0}};
        int[][] res = matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
    }
    private static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        // 初始化单位矩阵
        for (int i = 0; i < res.length; i++) res[i][i] = 1;
        // 矩阵1次方
        int[][] t = m;
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) res = multiMatrix(res, t);
            t = multiMatrix(t, t);
        }
        return res;
    }
    // 返回两个矩阵乘完之后的结果
    private static int[][] multiMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }

    /**
     * F(1) = 1, F(2) = 2, ..., F(n) = F(n - 1) + F(n - 2)
     */
    public static int s1(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return n;
        return s1(n - 1) + s1(n - 2);
    }
    public static int s2(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return n;
        int ans = 2, pre = 1, tmp = 0;
        for (int i = 3; i <= n; i++) {
            tmp = ans;
            ans += pre;
            pre = tmp;
        }
        return ans;
    }
    public static int s3(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return n;
        int[][] base = {{1, 1}, {1, 0}};
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    /**
     * F(1) = 1, F(2) = 2, F(3) = 3, ..., F(n) = F(n - 1) + F(n - 3)
     */
    public static int c1(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2 || n == 3) return n;
        return c1(n - 1) + c1(n - 3);
    }
    public static int c2(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2 || n == 3) return n;
        int ans = 3, pre = 2, prepre = 1, tmp1 = 0, tmp2 = 0;
        for (int i = 4; i <= n; i++) {
            tmp1 = ans;
            tmp2 = pre;
            ans += prepre;
            pre = tmp1;
            prepre = tmp2;
        }
        return ans;
    }
    public static int c3(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2 || n == 3) return n;
        // |1 1 0|
        // |0 0 1|
        // |1 0 0|
        int[][] base = {{1, 1, 0}, {0, 0, 1}, {1, 0, 0}};
        int[][] res = matrixPower(base, n - 3);
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
    }

    public static void main(String[] args) {
        int n = 19;
        System.out.println(f1(n));
        System.out.println(f2(n));
        System.out.println(f3(n));
        System.out.println("===");

        System.out.println(s1(n));
        System.out.println(s2(n));
        System.out.println(s3(n));
        System.out.println("===");

        System.out.println(c1(n));
        System.out.println(c2(n));
        System.out.println(c3(n));
        System.out.println("===");
    }
}
