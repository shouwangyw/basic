package com.yw.course.coding.class26;

import java.util.LinkedList;
import java.util.List;

/**
 * 测试链接: https://leetcode.cn/problems/expression-add-operators/
 * @author yangwei
 */
public class Code03_ExpressionAddOperators {

	public List<String> addOperators(String num, int target) {
		char[] nums = num.toCharArray();
		List<String> ans = new LinkedList<>();
		// 记录沿途的数字、+、-、*的决定
		char[] path = new char[nums.length * 2 - 1];
		long n = 0;
		for (int i = 0; i < nums.length; i++) {
			n = n * 10 + nums[i] - '0';
			path[i] = nums[i];
			dfs(ans, path, i + 1, 0, n, nums, i + 1, target);
			if (n == 0) break;
		}
		return ans;
	}
	// 固定参数：nums、target
	// ans: 收集答案，path: 之前做的决定（0...len-1），已经从左往右依次填写的字符在其中，可能含有'0'~'9' 与 * - +
	// left: 前面固定的部分，cur: 前面去掉固定的部分（例如: 当前来到 1*2-3，left=1*2=2、cur=-3）
	private static void dfs(List<String> ans, char[] path, int len, long left, long cur, char[] nums, int idx, int target) {
		if (idx == nums.length) {
			if (left + cur == target) ans.add(new String(path, 0, len));
			return;
		}
		long n = 0;
		for (int i = idx, j = len + 1; i < nums.length; i++) {
			// 尝试每一个可能的前缀(nums[idx...i])，作为第一个数字
			n = n * 10 + nums[i] - '0';
			path[j++] = nums[i];
			path[len] = '+';
			dfs(ans, path, j, left + cur, n, nums, i + 1, target);
			path[len] = '-';
			dfs(ans, path, j, left + cur, -n, nums, i + 1, target);
			path[len] = '*';
			dfs(ans, path, j, left, cur * n, nums, i + 1, target);
			if (nums[idx] == '0') break;
		}
	}

}
