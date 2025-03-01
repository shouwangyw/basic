package com.yw.course.coding.class39;

/**
 * 来自腾讯
 * 给定一个只由0和1组成的字符串S，假设下标从1开始，规定i位置的字符价值V[i]计算方式如下 :
 * 1) i == 1时，V[i] = 1
 * 2) i > 1时，如果S[i] != S[i-1]，V[i] = 1
 * 3) i > 1时，如果S[i] == S[i-1]，V[i] = V[i-1] + 1
 * 你可以随意删除S中的字符，返回整个S的最大价值
 * 字符串长度<=5000
 *
 * @author yangwei
 */
public class Code01_01AddValue {

	public static int maxValue(String s) {
		if (s == null || s.length() == 0) return 0;
		int[] arr = new int[s.length()];
		for (int i = 0; i < arr.length; i++) arr[i] = s.charAt(i) - '0';
		return maxValue(arr, 0, 0, 0);
	}
	// 当前来到idx位置做选择，原字符串s在idx位置左边最近的数字是lastNum，并且lastNum所带的价值已经被拉高到了baseValue
	// 返回在[idx...]范围做选择，最终获得的最大价值
	// index -> 0 ~ 4999，lastNum -> 0 or 1，baseValue -> 1 ~ 5000，5000 * 2 * 5000 -> 5 * 10^7 < 10^8 可以过!
	private static int maxValue(int[] arr, int idx, int lastNum, int baseValue) {
		if (idx == arr.length) return 0;
		// 当前价值
		int curValue = lastNum == arr[idx] ? baseValue + 1 : 1;
		// 递归调用
		// 1. 当前idx位置的字符保留
		int nextValue1 = maxValue(arr, idx + 1, arr[idx], curValue);
		// 2. 当前idx位置的字符不保留
		int nextValue2 = maxValue(arr, idx + 1, lastNum, baseValue);
		return Math.max(curValue + nextValue1, nextValue2);
	}

	// 请看体系学习班，动态规划章节，把上面的递归改成动态规划！看完必会

}
