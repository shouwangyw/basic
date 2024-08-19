package com.yw.course.coding.class33;

/**
 * @author yangwei
 */
public class Problem_0242_ValidAnagram {

	public boolean isAnagram(String s, String t) {
		if (s.length() != t.length()) return false;
		char[] cs = s.toCharArray(), ct = t.toCharArray();
		int[] cnt = new int[26];
		for (char x : cs) cnt[x - 'a']++;
		for (char x : ct) {
			if (--cnt[x - 'a'] < 0) return false;
		}
		return true;
	}

}
