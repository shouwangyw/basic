package com.yw.basic.algorithm.p35;

import java.util.Scanner;

/**
 * 给定 N、M，求 1 <= x <= N，1 <= y <= M 且 gcd(x, y) 为质数的 (x, y) 有多少对？
 * @author yangwei
 */
public class Solve {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            int m = in.nextInt();
            int ans = 0;
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    if (!isPrime(gcd(i, j))) continue;
                    ans += 1;
                    System.out.println(i + " " + j);
                }
            }
            System.out.println("total: "  + ans);
        }
    }

    private static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    private static boolean isPrime(int x) {
        if (x <= 1) return false;
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) return false;
        }
        return true;
    }
}
