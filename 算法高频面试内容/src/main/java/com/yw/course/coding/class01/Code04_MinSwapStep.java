package com.yw.course.coding.class01;

/**
 * 一个数组中只有两种字符'G'和'B'，
 * 可以让所有的G都放在左侧，所有的B都放在右侧
 * 或者可以让所有的G都放在右侧，所有的B都放在左侧
 * 但是只能在相邻字符之间进行交换操作，请问请问至少需要交换几次
 *
 * @author yangwei
 */
public class Code04_MinSwapStep {

    public static int minSteps0(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        int step1 = 0;
        int gi = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'G') {
                step1 += i - (gi++);
            }
        }
        int step2 = 0;
        int bi = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'B') {
                step2 += i - (bi++);
            }
        }
        return Math.min(step1, step2);
    }

    // 可以让G在左，或者在右
    public static int minSteps(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] cs = s.toCharArray();
        int ans1 = 0, ans2 = 0, gi = 0, bi = 0;
        for (int i = 0; i < cs.length; i++) {
            // 方案1: 当前的G去左边
            if (cs[i] == 'G') ans1 += i - (gi++);
            // 方案2: 当前的B去左边
            else ans2 += i - (bi++);
        }
        return Math.min(ans1, ans2);
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            String str = randomString(maxLen);
            int ans1 = minSteps0(str);
            int ans2 = minSteps(str);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }

    private static String randomString(int maxLen) {
        char[] str = new char[(int) (Math.random() * maxLen)];
        for (int i = 0; i < str.length; i++) {
            str[i] = Math.random() < 0.5 ? 'G' : 'B';
        }
        return String.valueOf(str);
    }
}