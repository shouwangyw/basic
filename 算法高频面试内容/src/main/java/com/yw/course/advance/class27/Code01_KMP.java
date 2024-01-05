package com.yw.course.advance.class27;

/**
 * @author yangwei
 */
public class Code01_KMP {

	// 方法一：暴力匹配
	// s: 母串，m: 模式串
	public static int indexOfByForce(String s, String m) {
		if (s == null || m == null || m.length() < 1 || s.length() < m.length()) return -1;
		char[] cs = s.toCharArray(), cm = m.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			boolean matched = true;
			for (int j = 0; j < cm.length; j++) {
				if (i + j < cs.length && cs[i + j] == cm[j]) continue;
				matched = false;
				break;
			}
			if (matched) return i;
		}
		return -1;
	}

	// 方法二：KMP匹配
	public static int indexOf(String s, String m) {
		if (s == null || m == null || m.length() < 1 || s.length() < m.length()) return -1;
		char[] cs = s.toCharArray(), cm = m.toCharArray();
		// 求next数组，时间复杂度: O(M) M <= N
		int[] next = getNext(cm);
		// KMP匹配，时间复杂度: O(N)
		int i = 0, j = 0;
		while (i < cs.length && j < cm.length) {
			// 如果能匹配上，则i、j一起往右走
			if (cs[i] == cm[j]) {
				i++;
				j++;
				// 都没匹配上，则母串s到下一个位置
			} else if (next[j] == -1) i++;
				// 否则模式串往右推
			else j = next[j];
		}
		return j == cm.length ? i - j : -1;
	}
	private static int[] getNext(char[] cm) {
		int[] next = new int[cm.length];
		next[0] = -1;
		if (cm.length == 1) return next;
		// i代表目前在哪个位置上求next数组的值
		// j代表当前是哪个位置的值在和i-1位置的字符比较
		int i = 2, j = 0;
		while (i < cm.length) {
			if (cm[i - 1] == cm[j]) next[i++] = ++j;
			else if (j > 0) j = next[j];
			else next[i++] = 0;
		}
		return next;
	}

	public static void main(String[] args) {
		int possibilities = 5;
		int strSize = 20;
		int matchSize = 5;
		int testTimes = 5000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			String str = getRandomString(possibilities, strSize);
			String match = getRandomString(possibilities, matchSize);

//			int ans1 = indexOfByForce(str, match);
			int ans1 = indexOf(str, match);
			int ans2 = str.indexOf(match);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				System.out.println(str + "|" + match + ": " + ans1 + " | " + ans2);
				break;
			}
		}
		System.out.println("test finish");
	}

	private static String getRandomString(int possibilities, int size) {
		char[] ans = new char[(int) (Math.random() * size) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}
}
