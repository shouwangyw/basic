package com.yw.advance.course.class17;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yangwei
 */
public class Code03_PrintAllSubSequences {

	// s -> "abc" ->
	public static List<String> subSequences(String s) {
		char[] str = s.toCharArray();
		String path = "";
		List<String> ans = new ArrayList<>();
		process(str, 0, ans, path);
		return ans;
	}
	// str 固定参数
	// 来到了str[index]字符，index是位置
	// str[0..index-1]已经走过了！之前的决定，都在path上
	// 之前的决定已经不能改变了，就是path
	// str[index....]还能决定，之前已经确定，而后面还能自由选择的话，
	// 把所有生成的子序列，放入到ans里去
	private static void process(char[] str, int index, List<String> ans, String path) {
		if (index == str.length) {
			ans.add(path);
			return;
		}
		// 没有要index位置的字符
		process(str, index + 1, ans, path);
		// 要了index位置的字符
		process(str, index + 1, ans, path + str[index]);
	}
	// 不重复排列
	public static List<String> subSequencesNoRepeat(String s) {
		char[] str = s.toCharArray();
		Set<String> set = new HashSet<>();
		process(str, 0, set, "");
		return new ArrayList<>(set);
	}
	private static void process(char[] str, int index, Set<String> set, String path) {
		if (index == str.length) {
			set.add(path);
			return;
		}
		process(str, index + 1, set, path);
		process(str, index + 1, set, path + str[index]);
	}

	public static void main(String[] args) {
		String test = "acccc";
		List<String> ans1 = subSequences(test);
		List<String> ans2 = subSequencesNoRepeat(test);

		for (String str : ans1) {
			System.out.println(str);
		}
		System.out.println("=================");
		for (String str : ans2) {
			System.out.println(str);
		}
		System.out.println("=================");
	}
}
