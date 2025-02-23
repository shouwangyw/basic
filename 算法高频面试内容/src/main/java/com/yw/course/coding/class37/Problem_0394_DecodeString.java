package com.yw.course.coding.class37;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author yangwei
 */
public class Problem_0394_DecodeString {

	public String decodeString(String s) {
		return process(s.toCharArray(), 0).ans;
	}
	// cs[i...] 遇到 ']' 或字符串s的终止位置时停止，返回Info
	private static Info process(char[] cs, int i) {
		StringBuilder ans = new StringBuilder();
		int cnt = 0; // 累加字符串次数
		while (i < cs.length && cs[i] != ']') {
			if ((cs[i] >= 'a' && cs[i] <= 'z') || (cs[i] >= 'A' && cs[i] <= 'Z')) ans.append(cs[i++]);
			else if (cs[i] >= '0' && cs[i] <= '9') cnt = cnt * 10 + (cs[i++] - '0');
			else {
				Info next = process(cs, i + 1);
				for (int j = 0; j < cnt; j++) ans.append(next.ans);
				cnt = 0;
				i = next.stop + 1;
			}
		}
		return new Info(ans.toString(), i);
	}
	private static class Info {
		public String ans;  // 解码的字符串
		public int stop;    // 算到了哪个位置
		public Info(String ans, int stop) {
			this.ans = ans;
			this.stop = stop;
		}
	}

	// 方法二：
	public String decodeString2(String s) {
		Deque<Integer> numStack = new LinkedList<>();
		Deque<StringBuilder> strStack = new LinkedList<>();
		StringBuilder ans = new StringBuilder();
		int num = 0;
		for (char c : s.toCharArray()) {
			if (Character.isDigit(c)) num = num * 10 + c - '0';
			else if (c == '[') {
				strStack.push(ans);
				numStack.push(num);
				ans = new StringBuilder();
				num = 0;
			} else if (Character.isAlphabetic(c)) ans.append(c);
			else {
				StringBuilder tmpStr = strStack.pop();
				int tmpNum = numStack.pop();
				for (int i = 0; i < tmpNum; i++) tmpStr.append(ans);
				ans = tmpStr;
			}
		}
		return ans.toString();
	}
}
