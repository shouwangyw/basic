package com.yw.course.coding.class10;

/**
 * 测试链接: https://leetcode.cn/problems/boolean-evaluation-lcci/description/
 * @author yangwei
 */
public class Code05_BooleanEvaluation {

	// 方法一：尝试，范围尝试模型
	// 举个例子，s整体长度10，最后以5位置符号计算整体结果，0-4范围整体结果为1方法数a种、整体结果为0方法数b种，6-9整体结果为1方法数c种、整体结果为0方法数d种
	// 若5位置符号&，要求result是1，则总方法数 = a * c；若要求result是0，则总方法数 = a * d + b * c + b * d
	public int countEval(String s, int result) {
		if (s == null || s.length() == 0) return 0;
		int n = s.length();
		Info[][] records = new Info[n][n];
		Info info = process(s.toCharArray(), 0, n - 1, records);
		return result == 1 ? info.t : info.f;
	}
	// l..r上一定有奇数个字符，l位置上的字符和r位置的字符，非0即1不能是逻辑符号
	// 返回cs[l...r]这一段为true的方法数和为false的方法数
	private static Info process(char[] cs, int l, int r, Info[][] records) {
		if (records[l][r] != null) return records[l][r];
		// base case: 只有一个字符
		if (l == r) return records[l][r] = new Info(cs[l] == '1' ? 1 : 0, cs[l] == '0' ? 1 : 0);
		// l...r >= 3，枚举每一个逻辑符号位置
		int t = 0, f = 0;
		for (int i = l + 1; i < r; i += 2) {
			Info leftInfo = process(cs, l, i - 1, records), rightInfo = process(cs, i + 1, r, records);
			int a = leftInfo.t, b = leftInfo.f, c = rightInfo.t, d = rightInfo.f;
			switch (cs[i]) {
				case '&': {
					t += a * c; f += a * d + b * c + b * d;
					break;
				}
				case '|': {
					t += a * c + a * d + b * c; f += b * d;
					break;
				}
				case '^': {
					t += a * d + b * c; f += a * c + b * d;
					break;
				}
			}
		}
		return records[l][r] = new Info(t, f);
	}
	private static class Info {
		private int t; // 为true方法数
		private int f; // 为false方法数
		public Info(int t, int f) { this.t = t; this.f = f; }
	}

	// 方法二：改动态规划
	public static int countEvalDp(String s, int result) {
		if (s == null || s.length() == 0) return 0;
		int n = s.length();
		char[] exp = s.toCharArray();
		int[][][] dp = new int[2][n][n];
		dp[0][0][0] = exp[0] == '0' ? 1 : 0;
		dp[1][0][0] = dp[0][0][0] ^ 1;
		for (int i = 2; i < exp.length; i += 2) {
			dp[0][i][i] = exp[i] == '1' ? 0 : 1;
			dp[1][i][i] = exp[i] == '0' ? 0 : 1;
			for (int j = i - 2; j >= 0; j -= 2) {
				for (int k = j; k < i; k += 2) {
					if (exp[k + 1] == '&') {
						dp[1][j][i] += dp[1][j][k] * dp[1][k + 2][i];
						dp[0][j][i] += (dp[0][j][k] + dp[1][j][k]) * dp[0][k + 2][i] + dp[0][j][k] * dp[1][k + 2][i];
					} else if (exp[k + 1] == '|') {
						dp[1][j][i] += (dp[0][j][k] + dp[1][j][k]) * dp[1][k + 2][i] + dp[1][j][k] * dp[0][k + 2][i];
						dp[0][j][i] += dp[0][j][k] * dp[0][k + 2][i];
					} else {
						dp[1][j][i] += dp[0][j][k] * dp[1][k + 2][i] + dp[1][j][k] * dp[0][k + 2][i];
						dp[0][j][i] += dp[0][j][k] * dp[0][k + 2][i] + dp[1][j][k] * dp[1][k + 2][i];
					}
				}
			}
		}
		return dp[result][0][n - 1];
	}
}
