package com.yw.course.advance.class27;

/**
 * @author yangwei
 */
public class Code03_IsRotation {

	public static boolean isRotation(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() != s2.length()) return false;
		return indexOf(s1 + s1, s2) != -1;
	}
	private static int indexOf(String s, String m) {
		char[] cs = s.toCharArray(), cm = m.toCharArray();
		int[] next = getNext(cm);
		int i = 0, j = 0;
		while (i < cs.length && j < cm.length) {
			if (cs[i] == cm[j]) {
				i++;
				j++;
			} else if (next[j] == -1) i++;
			else j = next[j];
		}
		return j == cm.length ? i - j : -1;
	}
	private static int[] getNext(char[] cm) {
		int[] next = new int[cm.length];
		next[0] = -1;
		if (cm.length == 1) return next;
		int i = 2, j = 0;
		while (i < cm.length) {
			if (cm[i - 1] == cm[j]) next[i++] = ++j;
			else if (j > 0) j = next[j];
			else next[i++] = 0;
		}
		return next;
	}

	public static void main(String[] args) {
		String str1 = "yunzuocheng";
		String str2 = "zuochengyun";
		System.out.println(isRotation(str1, str2));

	}

}
