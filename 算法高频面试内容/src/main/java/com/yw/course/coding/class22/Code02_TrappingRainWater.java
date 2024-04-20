package com.yw.course.coding.class22;

import java.util.Stack;

/**
 * 测试链接: https://leetcode.cn/problems/trapping-rain-water/
 *
 * @author yangwei
 */
public class Code02_TrappingRainWater {

    // 方法一：辅助数组
    public int trap0(int[] height) {
        int n = height.length;
        // left[i]表示0~i范围最大高度，right[i]表示i~n-1范围最大高度
        int[] left = new int[n], right = new int[n];
        for (int i = 0, max = 0; i < n; i++) {
            max = Math.max(max, height[i]);
            left[i] = max;
        }
        for (int i = n - 1, max = 0; i >= 0; i--) {
            max = Math.max(max, height[i]);
            right[i] = max;
        }
        // i位置水量 = min{left[i], right[i]} - height[i]，小于0过滤掉
        int ans = 0;
        for (int i = 1; i < n - 1; i++) {
            int w = Math.min(left[i - 1], right[i + 1]) - height[i];
            ans += w > 0 ? w : 0;
        }
        return ans;
    }

    // 方法二：最优解，左右双指针，记录左右最大值，最小结算谁
    public int trapOptimal(int[] height) {
        int n = height.length, l = 0, r = n - 1, lMax = height[l++], rMax = height[r--], ans = 0;
        while (l <= r) {
            if (lMax < rMax) {
                ans += Math.max(0, Math.min(lMax, rMax) - height[l]);
                lMax = Math.max(lMax, height[l++]);
            } else {
                ans += Math.max(0, Math.min(lMax, rMax) - height[r]);
                rMax = Math.max(rMax, height[r--]);
            }
        }
        return ans;
    }

    // 方法三：单调栈
    public int trap(int[] height) {
        int ans = 0;
        Stack<Integer> s = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            while (!s.isEmpty() && height[i] > height[s.peek()]) {
                int cur = s.pop();
                if (s.isEmpty()) continue;
                int w = i - s.peek() - 1;
                int h = Math.min(height[i] - height[cur], height[s.peek()] - height[cur]);
                ans += w * h;
            }
            s.push(i);
        }
        return ans;
    }
}
