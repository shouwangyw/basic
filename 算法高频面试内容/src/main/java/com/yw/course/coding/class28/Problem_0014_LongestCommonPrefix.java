package com.yw.course.coding.class28;

/**
 * @author yangwei
 */
public class Problem_0014_LongestCommonPrefix {

	public String longestCommonPrefix(String[] strs) {
		char[] cs = strs[0].toCharArray();
		int len = cs.length;
		for (int i = 1; i < strs.length; i++) {
			len = Math.min(len, strs[i].length());
			for (int j = 0; j < len; j++) {
				if (cs[j] != strs[i].charAt(j)) {
					len = j;
					break;
				}
			}
		}
		return strs[0].substring(0, len);
	}

}
