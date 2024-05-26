package com.yw.course.coding.class28;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class Problem_0022_GenerateParentheses {

	public List<String> generateParenthesis(int n) {
		List<String> ans = new ArrayList<>();
		char[] path = new char[n << 1];
		dfs(path, 0, 0, n, ans);
		return ans;
	}
	// l: 左括号数量、r: 右括号数量
	private void dfs(char[] path, int l, int r, int n, List<String> ans) {
		if (l + r == (n << 1)) ans.add(String.valueOf(path));
		else {
			// 剪枝: l < n 时才可以考虑放左括号
			if (l < n) {
				path[l + r] = '(';
				dfs(path, l + 1, r, n, ans);
			}
			// 剪枝: l > r 时才可以考虑放右括号
			if (l > r) {
				path[l + r] = ')';
				dfs(path, l, r + 1, n, ans);
			}
		}
	}

}
