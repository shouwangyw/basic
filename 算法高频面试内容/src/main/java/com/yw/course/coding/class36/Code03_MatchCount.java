package com.yw.course.coding.class36;

/**
 * 来自美团
 * 给定两个字符串s1和s2，返回在s1中有多少个子串等于s2
 *
 * @author yangwei
 */
public class Code03_MatchCount {

	public static int matchCount(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() < s2.length()) return 0;
		// cs: 母串，cm: 模式串
		char[] cs = s1.toCharArray(), cm = s2.toCharArray();
		// 求s2的next数组
		int[] next = getNext(cm);
		// i表示当前来到目串的位置，j表示模式串的位置
		int ans = 0, i = 0, j = 0;
		while (i < cs.length) {
			if (cs[i] == cm[j]) {
				// 匹配上
				i++;
				j++;
				// 如果已经匹配完，ans++，同时j通过next数组往前跳
				if (j == cm.length) {
					ans++;
					j = next[j];
				}
				// 没匹配上，则母串来到下一个位置
			} else if (next[j] == -1) i++;
			else j = next[j];
		}
		return ans;
	}
	// next数组需要多求一位，比如 s = "aaaa"，next = [-1,0,1,2,3]
	// 最后一个3表示终止位置的字符串 最长前缀和最长后缀的匹配长度
	private static int[] getNext(char[] cm) {
		int[] next = new int[cm.length + 1];
		next[0] = -1;
		// i表示当前在哪个位置求next数组的值，j表示当前是哪个位置的值在和i-1位置的字符比较
		int i = 2, j = 0;
		while (i < cm.length) {
			if (cm[i - 1] == cm[j]) next[i++] = ++j;
			else if (j > 0) j = next[j];
			else next[i++] = 0;
		}
		return next;
	}

	public static void main(String[] args) {
		String s1 = "abcdabcabccabcdefabc", s2 = "abc";

		System.out.println(matchCount(s1, s2));
	}

}
