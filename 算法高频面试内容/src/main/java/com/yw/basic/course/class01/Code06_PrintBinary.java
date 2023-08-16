package com.yw.basic.course.class01;

/**
 * 打印一个数的二进制形成
 *
 * @author yangwei
 */
public class Code06_PrintBinary {
    private static void print(int num) {
        System.out.printf("%d => \n", num);
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (i << i)) == 0 ? "0" : "1");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 32
        int num = 42361845;

        print(num);

        print(1);
        print(3);
        print(12);
    }
}
