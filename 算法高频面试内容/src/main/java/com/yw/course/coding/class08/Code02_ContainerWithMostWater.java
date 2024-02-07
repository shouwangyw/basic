package com.yw.course.coding.class08;

/**
 * 测试链接 : https://leetcode.cn/problems/container-with-most-water/
 * @author yangwei
 */
public class Code02_ContainerWithMostWater {

	public static int maxArea1(int[] h) {
		int max = 0;
		int N = h.length;
		for (int i = 0; i < N; i++) { // h[i]
			for (int j = i + 1; j < N; j++) { // h[j]
				max = Math.max(max, Math.min(h[i], h[j]) * (j - i));
			}
		}
		return max;
	}

	public int maxArea(int[] height) {
		int ans = 0, l = 0, r = height.length - 1;
		while (l < r) {
			ans = Math.max(ans, (r - l) * Math.min(height[l], height[r]));
			if (height[l] <= height[r]) l++;
			else r--;
		}
		return ans;
	}

}
