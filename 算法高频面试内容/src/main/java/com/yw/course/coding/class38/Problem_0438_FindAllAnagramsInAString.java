package com.yw.course.coding.class38;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangwei
 */
public class Problem_0438_FindAllAnagramsInAString {

	public static void main(String[] args) {
		findAnagrams("baa", "aa");
	}

	public static List<Integer> findAnagrams(String s, String p) {
		List<Integer> ans = new ArrayList<>();
		char[] cs = s.toCharArray(), cp = p.toCharArray();
		if (cs.length < cp.length) return ans;
		Map<Character, Integer> counter = new HashMap<>();
		for (char c : cp) counter.compute(c, (k, v) -> v == null ? 1 : v + 1);
		// 滑动窗口长度 cp.length
		int cnt = cp.length, l = 0, r = 0;
		while (r < cp.length) {
			if (counter.compute(cs[r++], (k, v) -> v == null ? -1 : v - 1) >= 0) cnt--;
		}
		if (cnt == 0) ans.add(l);
		while (r < cs.length) {
			// 左边出窗口一个字符
			if (!counter.containsKey(cs[l])) l++;
			else if (counter.compute(cs[l++], (k, v) -> v == null ? 1 : v + 1) > 0) cnt++;
			// 右边进窗口一个字符
			if (!counter.containsKey(cs[r])) r++;
			else if (counter.compute(cs[r++], (k, v) -> v == null ? -1 : v - 1) == 0) cnt--;
			if (cnt == 0) ans.add(l);
		}

		return ans;
	}
}
