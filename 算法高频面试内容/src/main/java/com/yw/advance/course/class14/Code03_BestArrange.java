package com.yw.advance.course.class14;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author yangwei
 */
public class Code03_BestArrange {

    public static class Program {
        int start;
        int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    // 方法一：暴力！所有情况都尝试！
    public static int bestArrangeTest(Program[] programs) {
        if (programs == null || programs.length == 0) return 0;
        return process(programs, 0, 0);
    }

    // 还剩下的会议都放在programs里
    // done之前已经安排了多少会议的数量
    // timeLine目前来到的时间点是什么
    // 目前来到timeLine的时间点，已经安排了done多的会议，剩下的会议programs可以自由安排
    // 返回能安排的最多会议数量
    private static int process(Program[] programs, int done, int timeLine) {
        if (programs.length == 0) return done;
        // 还剩下会议
        int max = done;
        // 当前安排的会议是什么会，每一个都枚举
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start >= timeLine) {
                Program[] next = copyButExcept(programs, i);
                max = Math.max(max, process(next, done + 1, programs[i].end));
            }
        }
        return max;
    }
    private static Program[] copyButExcept(Program[] programs, int i) {
        Program[] ans = new Program[programs.length - 1];
        int index = 0;
        for (int k = 0; k < programs.length; k++) {
            if (k != i) {
                ans[index++] = programs[k];
            }
        }
        return ans;
    }

    // 方法二：贪心
    // 会议的开始时间和结束时间，都是数值，不会 < 0
    public static int bestArrange(Program[] programs) {
        // 根据会议结束时间排序
        Arrays.sort(programs, Comparator.comparingInt(o -> o.end));
        int timeLine = 0;
        int result = 0;
        // 依次遍历每一个会议，结束时间早的会议先遍历
        for (int i = 0; i < programs.length; i++) {
            if (timeLine <= programs[i].start) {
                result++;
                timeLine = programs[i].end;
            }
        }
        return result;
    }

    // for test
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            ans[i] = r1 == r2 ? new Program(r1, r1 + 1) : new Program(Math.min(r1, r2), Math.max(r1, r2));
        }
        return ans;
    }

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int timeTimes = 1000000;
        for (int i = 0; i < timeTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            if (bestArrangeTest(programs) != bestArrange(programs)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
