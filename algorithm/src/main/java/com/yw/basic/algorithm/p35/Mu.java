package com.yw.basic.algorithm.p35;

import java.util.Scanner;

/**
 * @author yangwei
 */
public class Mu {
    private static final int MAX_N = 10000;
    private static int[] mu = new int[MAX_N + 5];
    private static int[] prime = new int[MAX_N + 5];
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            initPrime(n);
            for (int i = 1; i <= n; i++) {
                System.out.printf("mu(%d) = %d\n", i, mu[i]);
            }
        }
    }

    private static void initPrime(int n) {
        mu[1] = 1;
        for (int i = 2; i <= n; i++) {
            if (prime[i] == 0) {
                prime[++prime[0]] = i;
                mu[i] = -1;
            }
            for (int j = 1; j <= prime[0]; j++) {
                if (i * prime[j] > n) break;
                prime[i * prime[j]] = 1;
                if (i % prime[j] == 0) break;
                mu[i * prime[j]] = -mu[i];
            }
        }
    }
}
