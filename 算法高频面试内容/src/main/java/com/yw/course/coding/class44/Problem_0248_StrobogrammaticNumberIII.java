package com.yw.course.coding.class44;

/**
 * @author yangwei
 */
public class Problem_0248_StrobogrammaticNumberIII {

	public static int strobogrammaticInRange(String low, String high) {
		char[] lows = low.toCharArray(), highs = high.toCharArray();
		if (!isValidRange(lows, highs)) return 0;
		int ln = lows.length, hn = highs.length;
		if (ln == hn) return up(lows) - up(highs) + isValid(highs);
		int ans = 0;
		for (int i = ln + 1; i < hn; i++) ans += all(i);
		ans += up(lows) + down(highs);
		return ans;
	}
	// 校验范围是否有效
	private static boolean isValidRange(char[] ls, char[] hs) {
		// 1. 小数位数(长度)更小
		if (ls.length != hs.length) return ls.length < hs.length;
		// 2. 位数相等，依次比较高位
		for (int i = 0; i < ls.length; i++) {
			if (ls[i] == hs[i]) continue;
			return ls[i] < hs[i];
		}
		return true;
	}
	// 校验是否中心对称
	private static int isValid(char[] cs) {
		int l = 0, r = cs.length - 1;
		while (l <= r) {
			boolean diff = l != r;
			if (convert(cs[l++], diff) != cs[r--]) return 0;
		}
		return 1;
	}
	// 转成对称位置需要的数，diff: 对称位置是否与当前位置不同
	private static int convert(char c, boolean diff) {
		switch (c) {
			case '0':
			case '1':
			case '8': return c;
			case '6': return diff ? '9' : -1;
			case '9': return diff ? '6' : -1;
			default: return -1;
		}
	}
	// 如果是最开始 :
	// 	  Y X X X Y
	// -> 1 X X X 1
	// -> 8 X X X 8
	// -> 9 X X X 6
	// -> 6 X X X 9
	// 如果不是最开始 :
	//    Y X X X Y
	// -> 0 X X X 0
	// -> 1 X X X 1
	// -> 8 X X X 8
	// -> 9 X X X 6
	// -> 6 X X X 9
	// 所有长度为len位的数中有几个有效的
	public static int all(int len) {
		// 0位数0个，1位数有3个: 0, 1, 8
		int ans = (len & 1) == 0 ? 1 : 3;
		for (int i = (len & 1) == 0 ? 2 : 3; i < len; i += 2) {
			ans *= 5;
		}
		return ans << 2;
	}
	// 递归方式：
//	public static int all(int len, boolean init) {
//		// init == true，不可能调用all(0)
//		if (len == 0) return 1;
//		if (len == 1) return 3;
//		if (init) return all(len - 2, false) << 2;
//		else return all(len - 2, false) * 5;
//	}
	//
	private static int up(char[] lows) {
		return up(lows, 0, false, 1);
	}
	// left: 隐含right=n-1-left
	// lows [左边已经做完决定部分L left.....right 右边已经做完决定部分R]
	// 若L大于原始lows，则 lm = true；若L不大于原始lows，那一定是相等，不可能小于，lm = false
	// 若R小于原始lows，rs = 0；若R等于原始lows，rs = 1；若R大于原始lows，rs = 2
	// rs三种情况 < = >，分别对应 0 1 2
	// 返回[xx...x, 99...9]范围（比如: 234~999）有效对称数总数
	private static int up(char[] lows, int left, boolean lm, int rs) {
		int n = lows.length, right = n - 1 - left;
		if (left > right) return lm || (rs != 0) ? 1 : 0;
		// 若L部分已经大于原始lows，则中间部分直接用公式计算
		if (lm) return num(n - (left << 1));
		// 若L部分已经等于原始lows，比如0、1、8这些数
		int ways = 0;
		for (char c = (char) (lows[left] + 1); c <= '9'; c++) {
			if (convert(c, left != right) != -1) // 是否可以转
				ways += up(lows, left + 1, true, rs);
		}
		int con = convert(lows[left], left != right); // 是否可以转
		if (con != -1) {
			if (con < lows[right])
				ways += up(lows, left + 1, false, 0);
			else if (con == lows[right])
				ways += up(lows, left + 1, false, rs);
			else ways += up(lows, left + 1, false, 2);
		}
		return ways;
	}
	private static int down(char[] highs) {
		return down(highs, 0, false, 1);
	}
	// ll < =；rs < = >
	private static int down(char[] highs, int left, boolean ll, int rs) {
		int n = highs.length, right = n - 1 - left;
		if (left > right) return ll || (rs != 2) ? 1 : 0;
		if (ll) return num(n - (left << 1));
		int ways = 0;
		for (char c = (n != 1 && left == 0) ? '1' : '0'; c < highs[left]; c++) {
			if (convert(c, left != right) != -1)
				ways += down(highs, left + 1, true, rs);
		}
		int convert = convert(highs[left], left != right);
		if (convert != -1) {
			if (convert < highs[right])
				ways += down(highs, left + 1, false, 0);
			else if (convert == highs[right])
				ways += down(highs, left + 1, false, rs);
			else ways += down(highs, left + 1, false, 2);
		}
		return ways;
	}
	private static int num(int bits) {
		if (bits == 1) return 3;
		if (bits == 2) return 5;
		int ans = 0, p1 = 5, p2 = 3;
		for (int i = 3; i <= bits; i++) {
			ans = 5 * p2;
			p2 = p1;
			p1 = ans;
		}
		return ans;
	}
}
