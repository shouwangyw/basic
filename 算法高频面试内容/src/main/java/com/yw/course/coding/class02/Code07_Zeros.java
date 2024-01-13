package com.yw.course.coding.class02;

/**
 * @author yangwei
 */
public class Code07_Zeros {
    public static int tailZeros(int num) {
        int ans = 0;
        for (int i = 5; i <= num; i += 5) {
            // i 中有几个5的因子
            int x = i;
            while (x > 0 && x % 5 == 0) {
                x /= 5;
                ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(tailZeros(10));
        System.out.println(tailZeros(20));
        System.out.println(tailZeros(30));
        System.out.println(tailZeros(40));
        System.out.println(tailZeros(50));
        System.out.println(tailZeros(100));
        System.out.println(tailZeros(1000000));
    }
}
