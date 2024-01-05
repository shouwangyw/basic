package com.yw.course.basic.class05;

/**
 * 测试链接：https://leetcode.cn/problems/divide-two-integers
 *
 * @author yangwei
 */
public class Code02_BitAddMinusMultiDiv {
    // a^b => a与b不进位相加信息
    // a&b => a与b进位信息
    public static int add(int a, int b) {
        int sum = a;
        // 只要还有进位信息，循环继续
        while (b != 0) {
            sum = a ^ b;        // 不进位相加信息 -> sum
            b = (a & b) << 1;   // 进位信息 -> b -> b'
            a = sum;            // a -> a' 无进位相加信息
        }
        return sum;
    }

    // a-b => a + (-b) ==> add(a,-b) => add(a,~b+1) => add(a,add(~b,1))
    public static int minus(int a, int b) {
        return add(a, negNum(b));
    }

    /**
     *      0101101         45
     *  ×   0001010     ×   10
     *  -----------
     *      0000000
     *  +  0101101
     *  + 0000000
     *  +0101101
     *  -----------
     *    111000010     = 450
     */
    public static int multi(int a, int b) {
        int res = 0;
        while (b != 0) {
            if ((b & 1) != 0) {
                res = add(res, a);
            }
            a <<= 1;
            b >>>= 1;   // 不带符号位右移
        }
        return res;
    }

    /**
     * 1) a、b都是最小，返回1
     * 2) a不是最小，b最小，返回0
     * 3) a是最小，b不是最小
     *  3.1) b是-1，返回最大值
     *  3.2) b不是-1
     * 4) 其它
     */
    public static int divide(int a, int b) {
        if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) {
            return 1;
        } else if (b == Integer.MIN_VALUE) {
            return 0;
        } else if (a == Integer.MIN_VALUE) {
            if (b == negNum(1)) {
                return Integer.MAX_VALUE;
            } else {
                // !!!因为最小值没办法转成正的绝对值的最大值，所以先让a+1
                // a/b => (a+1)/b -> c
                // a-(b*c) => d 补偿值
                // d/b => e => 最后结果 c+e
                int c = div(add(a, 1), b);
                return add(c, div(minus(a, multi(c, b)), b));
            }
        } else {
            return div(a, b);
        }
    }

    private static int div(int a, int b) {
        // 先将a、b转为正数
        int x = isNeg(a) ? negNum(a) : a;
        int y = isNeg(b) ? negNum(b) : b;
        int res = 0;
        for (int i = 30; i >= 0; i = minus(i, 1)) {
            // 如果y左移可能溢出，所以让x往右移
            if ((x >> i) >= y) {
                // 移动到某个位置，够减y就将结果位 或进去(加进去)
                res |= (1 << i);
                // 然后x减掉 y<<i
                x = minus(x, y << i);
            }
        }
        // isNeg(a) ^ isNeg(b) => a与b符号相反，则结果取反
        return isNeg(a) ^ isNeg(b) ? negNum(res) : res;
    }

    private static int negNum(int n) {
        return add(~n, 1);
    }

    private static boolean isNeg(int n) {
        return n < 0;
    }

    public static void main(String[] args) {
//        System.out.println(divide(Integer.MIN_VALUE, -1));
        System.out.println(divide(Integer.MIN_VALUE, -3));
    }
}
