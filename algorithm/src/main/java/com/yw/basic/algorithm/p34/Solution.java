package com.yw.basic.algorithm.p34;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * a^x % b = c，已知 a，b 互质，给出 a、b、c 三个正整数，求 x 的最小正整数解。
 *
 * @author yangwei
 */
public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();

            int m = (int) Math.sqrt(Phi.phi(b));
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < m; i++) {
                map.put((int) Math.pow(a, i), m);
            }
            for (int i = 0; i < m; i++) {
                int a2 = (int) Math.pow(a, i);
                ExGcd.RefInt x = new ExGcd.RefInt();
                ExGcd.RefInt y = new ExGcd.RefInt();
                ExGcd.exGcd(a2, b, x, y);
                if (map.containsKey(x.i)) {
                    int k = map.get(x.i);
                    System.out.printf("x = %d\n", k * m + i);
                    break;
                }
            }
        }
    }
}
