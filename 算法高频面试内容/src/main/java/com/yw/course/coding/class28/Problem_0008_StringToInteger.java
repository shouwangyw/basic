package com.yw.course.coding.class28;

/**
 * @author yangwei
 */
public class Problem_0008_StringToInteger {

	public static int myAtoi(String s) {
		s = s.trim();
		if (s.length() == 0) return 0;
		int flag = 1, i = 0, ans = 0, max = Integer.MAX_VALUE, min = Integer.MIN_VALUE;
		char c0 = s.charAt(0);
		if (c0 == '-') flag = -1;
		if (c0 == '+' || c0 == '-') i++;
		while (i < s.length() && Character.isDigit(s.charAt(i))) {
			int si = s.charAt(i) - '0';
			// 判断 ans*10+si 后是否会溢出
			if (ans > max / 10 || ans == max / 10 && si > 7) {
				return flag > 0 ? max : min;
			}
			ans = ans * 10 + si;
			i++;
		}
		return flag * ans;
	}

	public static int myAtoi0(String s) {
		if (s == null || s.equals("")) {
			return 0;
		}
		s = removeHeadZero(s.trim());
		if (s == null || s.equals("")) {
			return 0;
		}
		char[] str = s.toCharArray();
		if (!isValid(str)) {
			return 0;
		}
		// str 是符合日常书写的，正经整数形式
		boolean posi = str[0] != '-';
		int minq = Integer.MIN_VALUE / 10;
		int minr = Integer.MIN_VALUE % 10;
		int res = 0;
		int cur = 0;
		for (int i = (str[0] == '-' || str[0] == '+') ? 1 : 0; i < str.length; i++) {
			// 3  cur = -3   '5'  cur = -5    '0' cur = 0
			cur = '0' - str[i];
			if ((res < minq) || (res == minq && cur < minr)) {
				return posi ? Integer.MAX_VALUE : Integer.MIN_VALUE;
			}
			res = res * 10 + cur;
		}
		// res 负
		if (posi && res == Integer.MIN_VALUE) {
			return Integer.MAX_VALUE;
		}
		return posi ? -res : res;
	}

	public static String removeHeadZero(String str) {
		boolean r = (str.startsWith("+") || str.startsWith("-"));
		int s = r ? 1 : 0;
		for (; s < str.length(); s++) {
			if (str.charAt(s) != '0') {
				break;
			}
		}
		// s 到了第一个不是'0'字符的位置
		int e = -1;
		// 左<-右
		for (int i = str.length() - 1; i >= (r ? 1 : 0); i--) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9') {
				e = i;
			}
		}
		// e 到了最左的 不是数字字符的位置
		return (r ? String.valueOf(str.charAt(0)) : "") + str.substring(s, e == -1 ? str.length() : e);
	}

	public static boolean isValid(char[] chas) {
		if (chas[0] != '-' && chas[0] != '+' && (chas[0] < '0' || chas[0] > '9')) {
			return false;
		}
		if ((chas[0] == '-' || chas[0] == '+') && chas.length == 1) {
			return false;
		}
		// 0 +... -... num
		for (int i = 1; i < chas.length; i++) {
			if (chas[i] < '0' || chas[i] > '9') {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(myAtoi("__021"));
		System.out.println(myAtoi0("__021"));
//		System.out.println(Integer.MAX_VALUE);
	}
	
	

}
