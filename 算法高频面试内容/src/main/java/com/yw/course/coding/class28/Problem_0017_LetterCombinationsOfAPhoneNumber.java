package com.yw.course.coding.class28;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class Problem_0017_LetterCombinationsOfAPhoneNumber {

	private static char[][] map = {
			{' '}, // 0
			{'_'}, // 1
			{'a', 'b', 'c'}, // 2
			{'d', 'e', 'f'}, // 3
			{'g', 'h', 'i'}, // 4
			{'j', 'k', 'l'}, // 5
			{'m', 'n', 'o'}, // 6
			{'p', 'q', 'r', 's'}, // 7
			{'t', 'u', 'v'}, // 8
			{'w', 'x', 'y', 'z'} // 9
	};
	public List<String> letterCombinations(String digits) {
		List<String> ans = new ArrayList<>();
		if (digits == null || digits.length() == 0) return ans;
		char[] cs = digits.toCharArray(), path = new char[cs.length];
		dfs(cs, 0, path, ans);
		return ans;
	}
	private static void dfs(char[] cs, int idx, char[] path, List<String> ans) {
		if (idx == cs.length) ans.add(String.valueOf(path));
		else {
			for (char c : map[cs[idx] - '0']) {
				path[idx] = c;
				dfs(cs, idx + 1, path, ans);
			}
		}
	}

}
