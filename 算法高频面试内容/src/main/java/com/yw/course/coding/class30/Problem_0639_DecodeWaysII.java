package com.yw.course.coding.class30;

/**
 * @author yangwei
 */
public class Problem_0639_DecodeWaysII {

	// 方法一：暴力递归
	public int numDecodings0(String s) {
		return process(s.toCharArray(), 0);
	}
	// 返回[i...]有多少种解码方式
	private static int process(char[] cs, int i) {
		if (i == cs.length) return 1;
		if (cs[i] == '0') return 0;
		// 下面开始分情况讨论
		// 1. i位置不是*，则一定是1~9
		if (cs[i] != '*') {
			// 1.1 i位置单独解码
			int p1 = process(cs, i + 1);
			// 1.2 i 和 i+1一起解码
			if (i + 1 == cs.length) return p1;
			// 1.2.1 i+1位置不是'*'
			if (cs[i + 1] != '*') {
				int num = (cs[i] - '0') * 10 + (cs[i + 1] - '0');
				if (num < 27) return p1 + process(cs, i + 2);
			} else { // 1.2.2 i+1位置是'*'
				if (cs[i] < '3') return p1 + (cs[i] == '1' ? 9 : 6) * process(cs, i + 2);
			}
			return p1;
		}
		// 2. i位置是*
		// 2.1 i位置单独解码，1~9 共9种
		int p1 = 9 * process(cs, i + 1);
		// 2.2 i 和 i+1一起解码
		if (i + 1 == cs.length) return p1;
		// 2.2.1 i+1位置不是'*'，则是1~9，*0~*6 都有2种、*7~*8有1种
		if (cs[i + 1] != '*') return p1 + (cs[i + 1] < '7' ? 2 : 1) * process(cs, i + 2);
			// 2.2.2 i+1位置是'*'，11~19、21~26 共 15 种
		else return p1 + 15 * process(cs, i + 2);
	}

	// 方法二：傻缓存法
	private static long mod = 1000000007L;
	public int numDecodingsByCache(String s) {
		long[] cache = new long[s.length()];
		return (int) process(s.toCharArray(), 0, cache);
	}
	// 返回[i...]有多少种解码方式
	private static long process(char[] cs, int i, long[] cache) {
		if (i == cs.length) return 1;
		if (cs[i] == '0') return 0;
		if (cache[i] != 0) return cache[i];
		long ans = (cs[i] == '*' ? 9 : 1) * process(cs, i + 1, cache);
		if (cs[i] == '1' || cs[i] == '2' || cs[i] == '*') {
			if (i + 1 < cs.length) {
				if (cs[i + 1] == '*') ans += (cs[i] == '*' ? 15 : (cs[i] == '1' ? 9 : 6)) * process(cs, i + 2, cache);
				else {
					if (cs[i] == '*') ans += (cs[i + 1] < '7' ? 2 : 1) * process(cs, i + 2, cache);
					else if ((cs[i] - '0') * 10 + (cs[i + 1] - '0') < 27) ans += process(cs, i + 2, cache);
				}
			}
		}
		ans %= mod;
		return cache[i] = ans;
	}

	// 方法三：动态规划
	public int numDecodingsByDp(String s) {
		char[] cs = s.toCharArray();
		int n = cs.length;
		long[] dp = new long[n + 1];
		dp[n] = 1;
		for (int i = n - 1; i >= 0; i--) {
			if (cs[i] == '0') continue;
			dp[i] = (cs[i] == '*' ? 9 : 1) * dp[i + 1];
			if (cs[i] != '*' && cs[i] - '0' >= 3 || i + 1 == n) continue;
			if (cs[i + 1] == '*') dp[i] += (cs[i] == '*' ? 15 : (cs[i] == '1' ? 9 : 6)) * dp[i + 2];
			else {
				if (cs[i] == '*') dp[i] += (cs[i + 1] < '7' ? 2 : 1) * dp[i + 2];
				else if ((cs[i] - '0') * 10 + (cs[i + 1] - '0') < 27) dp[i] += dp[i + 2];
			}
			dp[i] %= 1000000007L;
		}
		return (int) dp[0];
	}

	// 方法四：最优解
	public int numDecodings(String s) {
		char[] cs = s.toCharArray();
		int n = cs.length;
		long a = 1, b = 1, c = 0;
		for (int i = n - 1; i >= 0; i--) {
			if (cs[i] != '0') {
				c = b * (cs[i] == '*' ? 9 : 1);
				if ((cs[i] == '*' || cs[i] == '1' || cs[i] == '2') && (i + 1 < n)) {
					if (cs[i + 1] == '*') c += a * (cs[i] == '*' ? 15 : (cs[i] == '1' ? 9 : 6));
					else {
						if (cs[i] == '*') c += a * (cs[i + 1] < '7' ? 2 : 1);
						else c += a * ((cs[i] - '0') * 10 + (cs[i + 1] - '0') < 27 ? 1 : 0);
					}
				}
			}
			c %= mod;
			a = b;
			b = c;
			c = 0;
		}
		return (int) b;
	}

}
