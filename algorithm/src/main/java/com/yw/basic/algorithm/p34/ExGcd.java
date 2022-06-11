package com.yw.basic.algorithm.p34;

import java.util.*;

/**
 * @author yangwei
 */
public class ExGcd {
    public static class RefInt {
        int i;
    }

    public static int exGcd(int a, int b, RefInt x, RefInt y) {
        if (b == 0) {
            x.i = 1;
            y.i = 0;
            return a;
        }
        int r = exGcd(b, a % b, y, x);
        y.i -= a / b * x.i;
        return r;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int a = in.nextInt();
            int b = in.nextInt();
            RefInt x = new RefInt();
            RefInt y = new RefInt();
            int c = exGcd(a, b, x, y);
            System.out.printf("%d * %d + %d * %d = %d\n", a, x.i, b, y.i, c);
        }
    }
}
