package com.yw.course.advance.class17;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class Code04_PrintAllPermutations {

	// 方法一：
	public static List<String> permutation(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() == 0) return ans;
		List<Character> cs = new ArrayList<>();
		for (char c : s.toCharArray()) cs.add(c);
		process(cs, "", ans);
		return ans;
	}
	private static void process(List<Character> cs, String path, List<String> ans) {
		if (cs.isEmpty()) ans.add(path);
		else {
			int size = cs.size();
			for (int i = 0; i < size; i++) {
				Character c = cs.get(i);
				cs.remove(c);
				process(cs, path + c, ans);
				cs.add(i, c);
			}
		}
	}
	// 方法二：
	public static List<String> permutation2(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() == 0) return ans;
		process(s.toCharArray(), 0, ans);
		return ans;
	}
	private static void process(char[] str, int index, List<String> ans) {
		if (index == str.length) {
			ans.add(String.valueOf(str));
		} else {
			for (int i = index; i < str.length; i++) {
				swap(str, index, i);
				process(str, index + 1, ans);
				swap(str, index, i);
			}
		}
	}
	// 不重复排列
	public static List<String> permutationNoRepeat(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() == 0) return ans;
		processNoRepeat(s.toCharArray(), 0, ans);
		return ans;
	}
	private static void processNoRepeat(char[] str, int index, List<String> ans) {
		if (index == str.length) {
			ans.add(String.valueOf(str));
		} else {
			boolean[] visited = new boolean[256];
			for (int i = index; i < str.length; i++) {
				if (visited[str[i]]) continue;
				visited[str[i]] = true;
				swap(str, index, i);
				processNoRepeat(str, index + 1, ans);
				swap(str, index, i);
			}
		}
	}
	private static void swap(char[] chs, int i, int j) {
		if (i == j) return;
		char tmp = chs[i];
		chs[i] = chs[j];
		chs[j] = tmp;
	}

	public static void main(String[] args) {
		String s = "acc";
		List<String> ans1 = permutation(s);
		for (String str : ans1) {
			System.out.println(str);
		}
		System.out.println("=======");
		List<String> ans2 = permutation2(s);
		for (String str : ans2) {
			System.out.println(str);
		}
		System.out.println("=======");
		List<String> ans3 = permutationNoRepeat(s);
		for (String str : ans3) {
			System.out.println(str);
		}
	}
}
