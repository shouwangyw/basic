package com.yw.course.coding.class08;

import java.util.LinkedList;

/**
 * 测试链接 : https://leetcode.cn/problems/basic-calculator-iii/
 * @author yangwei
 */
public class Code01_ExpressionCompute {

	public static int calculate(String str) {
		return f(str.toCharArray(), 0)[0];
	}
	// 从cs[i,...]往下计算，遇到右括号或者字符终止位置停止，返回长度为2的数组，[0]表示这一段计算的结果、[1]表示计算到了哪个位置
	private static int[] f(char[] cs, int i) {
		LinkedList<String> q = new LinkedList<>();
		int cur = 0;
		while (i < cs.length && cs[i] != ')') {
			if (cs[i] >= '0' && cs[i] <= '9') cur = cur * 10 + cs[i++] - '0';
			else if (cs[i] != '(') {
				// 到这，说明遇到的是运算符
				addNum(q, cur);
				q.addLast(String.valueOf(cs[i++]));
				cur = 0;
			} else {
				// 遇到左括号
				int[] next = f(cs, i + 1);
				cur = next[0];
				i = next[1] + 1;
			}
		}
		addNum(q, cur);
		return new int[] { getNum(q), i };
	}
	private static void addNum(LinkedList<String> q, int num) {
		if (!q.isEmpty()) {
			String top = q.pollLast();
			if ("+".equals(top) || "-".equals(top)) q.addLast(top);
			else {
				int cur = Integer.parseInt(q.pollLast());
				num = "*".equals(top) ? (cur * num) : (cur / num);
			}
		}
		q.addLast(String.valueOf(num));
	}
	private static int getNum(LinkedList<String> stack) {
		int ans = 0;
		boolean add = true;
		String cur;
		while (!stack.isEmpty()) {
			cur = stack.pollFirst();
			if ("+".equals(cur)) add = true;
			else if ("-".equals(cur)) add = false;
			else {
				int num = Integer.parseInt(cur);
				ans += add ? num : (-num);
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		System.out.println(calculate("48*((70-65)-43)+8*1"));
		System.out.println(calculate("3+1*4"));
		System.out.println(calculate("3+(1*4)"));
	}
}
