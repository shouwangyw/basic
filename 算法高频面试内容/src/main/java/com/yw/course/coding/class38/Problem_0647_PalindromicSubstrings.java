package com.yw.course.coding.class38;

/**
 * @author yangwei
 */
public class Problem_0647_PalindromicSubstrings {

	public int countSubstrings(String s) {
		char[] ms = getManachers(s);
		int n = ms.length, l = 0, r = -1, cnt = 0;
		int[] d = new int[n]; // 记录每个中心的最大回文半径
		for (int i = 0; i < n; i++) {
			// 初始化半径：若i在已知边界，初始化为1；否则利用对称性快速初始化
			d[i] = (i > r) ? 1 : Math.min(r - i, d[r - i + l]);
			// 扩展半径：左右字符串匹配时，扩大半径
			while (i - d[i] >= 0 && i + d[i] < n && ms[i - d[i]] == ms[i + d[i]]) d[i]++;
			// 更新左右回文边界
			if (i - d[i] > 0 && i + d[i] > r) {
				l = i - d[i];
				r = i + d[i];
			}
			cnt += d[i] / 2; // 累加当前中心的回文子串数
		}
		return cnt;
	}
	// 预处理，生成manacher串
	private char[] getManachers(String s) {
		char[] ms = new char[(s.length() << 1) + 1];
		for (int i = 0; i < ms.length; i += 2) ms[i] = '#';
		for (int i = 1; i < ms.length; i += 2) ms[i] = s.charAt(i >> 1);
		return ms;
	}

}
