package com.yw.basic.algorithm.p34;

import java.util.Scanner;

/**
 * @author yangwei
 */
public class Phi {
    public static int phi(int n) {
        int x = 2, ans = n;
        while (x * x <= n) {
            if (n % x == 0) ans -= ans / x;
            while (n % x == 0) n /= x;
            x += 1;
        }
        if (n != 1) ans -= ans / n;
        return ans;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            System.out.printf("phi(%d) = %d\n", n, phi(n));
        }
    }
}
