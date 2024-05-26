package com.yw.course.coding.class28;

import java.util.*;

/**
 * @author yangwei
 */
public class Problem_0049_GroupAnagrams {

	public List<List<String>> groupAnagrams(String[] strs) {
		Map<String, List<String>> map = new HashMap<>();
		for (String s : strs) {
			// 遍历每一个字符串，将当前字符串按字母排序，放到map里
			map.compute(covert(s), (k, v) -> {
				if (v == null) v = new ArrayList<>();
				v.add(s);
				return v;
			});
		}
		return new ArrayList<>(map.values());
	}
	private String covert(String s) {
		char[] cs = s.toCharArray();
		Arrays.sort(cs);
		return new String(cs);
	}

}
