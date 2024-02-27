package com.yw.course.coding.class09;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试链接 : https://leetcode.cn/problems/remove-invalid-parentheses/
 * @author yangwei
 */
public class Code02_RemoveInvalidParentheses {

	// 来自leetcode投票第一的答案，实现非常好，我们来赏析一下
	public List<String> removeInvalidParentheses(String s) {
		List<String> ans = new ArrayList<>();
		remove(s, ans, 0, 0, '(', ')');
		return ans;
	}
	// checkIdx: 检查位置，deleteIdx: 删除位置，l: 左字符，r: 右字符
	private static void remove(String s, List<String> ans, int checkIdx, int deleteIdx, char l, char r) {
		for (int cnt = 0, i = checkIdx; i < s.length(); i++) {
			if (s.charAt(i) == l) cnt++;
			if (s.charAt(i) == r) cnt--;
			if (cnt >= 0) continue;
			// cnt<0: 出现第一个不合法位置
			for (int j = deleteIdx; j <= i; ++j) {
				if (s.charAt(j) == r && (j == deleteIdx || s.charAt(j - 1) != r))
					remove(s.substring(0, j) + s.substring(j + 1), ans, i, j, l, r);
			}
			return;
		}
		String reversed = new StringBuilder(s).reverse().toString();
		if (l == '(') remove(reversed, ans, 0, 0, r, l);
		else ans.add(reversed);
	}
//	public static List<String> removeInvalidParentheses(String s) {
//		List<String> ans = new ArrayList<>();
//		remove(s, ans, 0, 0, new char[] { '(', ')' });
//		return ans;
//	}
//
//	// modifyIndex <= checkIndex
//	// 只查s[checkIndex....]的部分，因为之前的一定已经调整对了
//	// 但是之前的部分是怎么调整对的，调整到了哪？就是modifyIndex
//	// 比如：
//	// ( ( ) ( ) ) ) ...
//	// 0 1 2 3 4 5 6
//	// 一开始当然checkIndex = 0，modifyIndex = 0
//	// 当查到6的时候，发现不对了，
//	// 然后可以去掉2位置、4位置的 )，都可以
//	// 如果去掉2位置的 ), 那么下一步就是
//	// ( ( ( ) ) ) ...
//	// 0 1 2 3 4 5 6
//	// checkIndex = 6 ，modifyIndex = 2
//	// 如果去掉4位置的 ), 那么下一步就是
//	// ( ( ) ( ) ) ...
//	// 0 1 2 3 4 5 6
//	// checkIndex = 6 ，modifyIndex = 4
//	// 也就是说，
//	// checkIndex和modifyIndex，分别表示查的开始 和 调的开始，之前的都不用管了  par  (  )
//	public static void remove(String s, List<String> ans, int checkIndex, int deleteIndex, char[] par) {
//		for (int count = 0, i = checkIndex; i < s.length(); i++) {
//			if (s.charAt(i) == par[0]) {
//				count++;
//			}
//			if (s.charAt(i) == par[1]) {
//				count--;
//			}
//			// i check计数<0的第一个位置
//			if (count < 0) {
//				for (int j = deleteIndex; j <= i; ++j) {
//					// 比如
//					if (s.charAt(j) == par[1] && (j == deleteIndex || s.charAt(j - 1) != par[1])) {
//						remove(
//								s.substring(0, j) + s.substring(j + 1),
//								ans, i, j, par);
//					}
//				}
//				return;
//			}
//		}
//		String reversed = new StringBuilder(s).reverse().toString();
//		if (par[0] == '(') {
//			remove(reversed, ans, 0, 0, new char[] { ')', '(' });
//		} else {
//			ans.add(reversed);
//		}
//	}

}
