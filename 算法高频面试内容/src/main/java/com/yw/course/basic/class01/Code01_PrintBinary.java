package com.yw.course.basic.class01;

/**
 * @author yangwei
 */
public class Code01_PrintBinary {

    public static void print(int num) {
        // int 32位，依次打印高位到低位(31 -> 0)的二进制值
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        print(42361845);
        print(Integer.MAX_VALUE);
        print(Integer.MIN_VALUE);
//
//		
//		int b = 123823138;
//		int c = ~b;
//		print(b);
//		print(c);

//		print(-5);

//		System.out.println(Integer.MIN_VALUE);
//		System.out.println(Integer.MAX_VALUE);

//		int a = 12319283;
//		int b = 3819283;
//		print(a);
//		print(b);
//		System.out.println("=============");
//		print(a | b);
//		print(a & b);
//		print(a ^ b);

//		int a = Integer.MIN_VALUE;
//		print(a);
//		print(a >> 1);		// 带符合右移
//		print(a >>> 1);		// 不带符合右移
//		
//		int c = Integer.MIN_VALUE;
//		int d = -c ;
//		
//		print(c);
//		print(d);
    }
}
