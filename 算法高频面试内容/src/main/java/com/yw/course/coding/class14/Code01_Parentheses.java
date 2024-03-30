package com.yw.course.coding.class14;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 测试链接 : https://leetcode.cn/problems/longest-valid-parentheses/
 *
 * @author yangwei
 */
public class Code01_Parentheses {

	public static boolean valid(String s) {
		char[] str = s.toCharArray();
		int count = 0;
		for (int i = 0; i < str.length; i++) {
			count += str[i] == '(' ? 1 : -1;
			if (count < 0) {
				return false;
			}
		}
		return count == 0;
	}

	public static int needParentheses(String s) {
		char[] str = s.toCharArray();
		int count = 0;
		int need = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') {
				count++;
			} else { // 遇到的是')'
				if (count == 0) {
					need++;
				} else {
					count--;
				}
			}
		}
		return count + need;
	}

	public static boolean isValid(char[] str) {
		if (str == null || str.length == 0) {
			return false;
		}
		int status = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] != ')' && str[i] != '(') {
				return false;
			}
			if (str[i] == ')' && --status < 0) {
				return false;
			}
			if (str[i] == '(') {
				status++;
			}
		}
		return status == 0;
	}

	public static int deep(String s) {
		char[] str = s.toCharArray();
		if (!isValid(str)) {
			return 0;
		}
		int count = 0;
		int max = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') {
				max = Math.max(max, ++count);
			} else {
				count--;
			}
		}
		return max;
	}

	// 方法一：动态规划
	public int longestValidParentheses(String s) {
		char[] cs = s.toCharArray();
		int n = cs.length, ans = 0, pre = 0;
		// dp[i]: 以i位置结尾的最长有效子串长度
		int[] dp = new int[n];
		for (int i = 1; i < n; i++) {
			if (cs[i] == ')') {
				// 当前谁和i位置的右括号匹配
				pre = i - dp[i - 1] - 1;
				if (pre >= 0 && cs[pre] == '(')
					dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
			}
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

	// 方法二：
	public int longestValidParentheses0(String s) {
		Deque<Integer> stack = new LinkedList<>();
		stack.offerLast(-1);
		char[] cs = s.toCharArray();
		int ans = 0;
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] == '(') stack.offerLast(i);
			else {
				stack.pollLast();
				if (stack.isEmpty()) stack.offerLast(i);
				else ans = Math.max(ans, i - stack.peekLast());
			}
		}
		return ans;
	}

}
