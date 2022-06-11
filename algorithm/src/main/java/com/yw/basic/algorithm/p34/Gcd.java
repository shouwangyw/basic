package com.yw.basic.algorithm.p34;

import java.util.Scanner;

/**
 * @author yangwei
 */
public class Gcd {
    public static int gcd(int a, int b) {
        System.out.printf("gcd(%d, %d) = ", a, b);
        if (b == 0) return a;
        return gcd(b, a % b);
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(gcd(a, b));
        }
    }
}
