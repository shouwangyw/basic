package com.yw.course.coding.class36;

/**
 * 来自美团
 * () 分值为2
 * (()) 分值为3
 * ((())) 分值为4
 * 也就是说，每包裹一层，分数就是里面的分值+1
 * ()() 分值为2 * 2
 * (())() 分值为3 * 2
 * 也就是说，每连接一段，分数就是各部分相乘，以下是一个结合起来的例子
 * (()())()(()) -> (2 * 2 + 1) * 2 * 3 -> 30
 * 给定一个括号字符串str，已知str一定是正确的括号结合，不会有违规嵌套
 * 返回分数
 *
 * @author yangwei
 */
public class Code04_ComputeExpressionValue {
	public static int calcScore(String s) {
		return calcScore(s.toCharArray(), 0)[0];
	}
	// cs当遇到')'或者终止位置时-停止
	// 返回值int[]长度2，[0]表示分数，[1]位置表示计算到什么位置
	private static int[] calcScore(char[] cs, int idx) {
		if (cs[idx] == ')') return new int[] {1, idx};
		int ans = 1;
		while (idx < cs.length && cs[idx] != ')') {
			int[] res = calcScore(cs, idx + 1);
			// 返回的结果+1后，乘到原ans里去，并且idx来到下一个位置
			ans *= res[0] + 1;
			idx = res[1] + 1;
		}
		return new int[] { ans, idx };
	}

	public static void main(String[] args) {

		String str1 = "(()())()(())";
		System.out.println(calcScore(str1));

		// (()()) + (((()))) + ((())())
		// (()()) -> 2 * 2 + 1 -> 5
		// (((()))) -> 5
		// ((())()) -> ((2 + 1) * 2) + 1 -> 7
		// 所以下面的结果应该是175
		String str2 = "(()())(((())))((())())";
		System.out.println(calcScore(str2));

		// (()()()) + (()(()))
		// (()()()) -> 2 * 2 * 2 + 1 -> 9
		// (()(())) -> 2 * 3 + 1 -> 7
		// 所以下面的结果应该是63
		String str3 = "(()()())(()(()))";
		System.out.println(calcScore(str3));
	}

}
