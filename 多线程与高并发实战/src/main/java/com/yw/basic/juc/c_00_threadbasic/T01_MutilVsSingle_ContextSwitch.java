package com.yw.basic.juc.c_00_threadbasic;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author yangwei
 */
public class T01_MutilVsSingle_ContextSwitch {
    private static double[] nums = new double[1_0000_0000];
    private static Random r = new Random();
    private static DecimalFormat df = new DecimalFormat("0.00");
    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextDouble();
        }
    }
    private static void m1() {
        long start = System.currentTimeMillis();
        double result = 0.0;
        for (int i = 0; i < nums.length; i++) {
            result += nums[i];
        }
        long end = System.currentTimeMillis();
        System.out.println("m1: " + (end - start) + " result = " + df.format(result));
    }
    /******************************************************/
    static double result1 = 0.0, result2 = 0.0, result = 0.0;
    private static void m2() throws Exception {

    }
}
