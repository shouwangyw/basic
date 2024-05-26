package com.yw.course.coding.class28;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class Problem_0013_RomanToInteger {

	public int romanToInt(String s) {
		Map<Character, Integer> map = new HashMap(){{
			put('I', 1);
			put('V', 5);
			put('X', 10);
			put('L', 50);
			put('C', 100);
			put('D', 500);
			put('M', 1000);
		}};
		int ans = 0;
		for (int i = 0; i < s.length(); i++) {
			int x = map.get(s.charAt(i)), y = i == s.length() - 1 ? 0 : map.get(s.charAt(i + 1));
			ans += x < y ? -x : x;
		}
		return ans;
	}

}
