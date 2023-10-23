package com.yw.advance.course.class19;

/**
 * @author yangwei
 */
public class Code02_ConvertToLetterString {

	// 方法一：尝试暴力递归
	// str只含有数字字符0~9，返回多少种转化方案
	public static int numberByRecurse(String str) {
		if (str == null || str.length() == 0) return 0;
		return process(str.toCharArray(), 0);
	}
	// cs 在 [i, n - 1] 转化，返回有多少种转化方法
	private static int process(char[] cs, int i) {
		if (i == cs.length) return 1;
		// i没到最后，说明有字符
		if (cs[i] == '0') return 0;  // 之前的决定有问题
		// str[i] != '0'
		int ans = process(cs, i + 1);
		if (i + 1 < cs.length && (cs[i] - '0') * 10 + cs[i + 1] - '0' < 27)
			ans += process(cs, i + 2);
		return ans;
	}

	// 方法二：动态规划（从右往左）
	public static int numberByDpRightToLeft(String str) {
		if (str == null || str.length() == 0) return 0;
		char[] cs = str.toCharArray();
		int n = cs.length;
		int[] dp = new int[n + 1];
		dp[n] = 1;
		for (int i = n - 1; i >= 0; i--) {
			if (cs[i] == '0') continue;
			int ans = dp[i + 1];
			if (i + 1 < cs.length && (cs[i] - '0') * 10 + cs[i + 1] - '0' < 27)
				ans += dp[i + 2];
			dp[i] = ans;
		}
		return dp[0];
	}

	// 方法三：动态规划（从左往右）
	public static int numberByDpLeftToRight(String str) {
		if (str == null || str.length() == 0) return 0;
		char[] cs = str.toCharArray();
		int n = cs.length;
		if (cs[0] == '0') return 0;
		int[] dp = new int[n];
		dp[0] = 1;
		for (int i = 1; i < n; i++) {
			if (cs[i] == '0') {
				// 如果此时str[i]=='0'，那么他是一定要拿前一个字符(i-1的字符)一起拼的，
				// 那么就要求前一个字符，不能也是‘0’，否则拼不了。
				// 前一个字符不是‘0’就够了嘛？不够，还得要求拼完了要么是10，要么是20，如果更大的话，拼不了。

				// 这就够了嘛？还不够，你们拼完了，还得要求str[0...i-2]真的可以被分解！
				// 如果str[0...i-2]都不存在分解方案，那i和i-1拼成了也不行，因为之前的搞定不了
				// ？？？ i - 2 >= 0 && dp[i - 2] == 0 这种情况应该不存在
//				if (cs[i - 1] == '0' || cs[i - 1] > '2' || (i - 2 >= 0 && dp[i - 2] == 0))

				if (cs[i - 1] == '0' || cs[i - 1] > '2')
					return 0;
				else dp[i] = i - 2 >= 0 ? dp[i - 2] : 1;
			} else {
				dp[i] = dp[i - 1];
				if (cs[i - 1] != '0' && (cs[i - 1] - '0') * 10 + cs[i] - '0' <= 26)
					dp[i] += i - 2 >= 0 ? dp[i - 2] : 1;
			}
		}
		return dp[n - 1];
	}

	public static void main(String[] args) {
		int N = 30;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N);
			String s = randomString(len);
			int ans0 = numberByRecurse(s);
			int ans1 = numberByDpRightToLeft(s);
			int ans2 = numberByDpLeftToRight(s);
			if (ans0 != ans1 || ans0 != ans2) {
				System.out.println(s);
				System.out.println(ans0);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}

	private static String randomString(int len) {
		char[] str = new char[len];
		for (int i = 0; i < len; i++) {
			str[i] = (char) ((int) (Math.random() * 10) + '0');
		}
		return String.valueOf(str);
	}
}
