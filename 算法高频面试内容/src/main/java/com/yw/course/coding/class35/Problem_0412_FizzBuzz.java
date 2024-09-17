package com.yw.course.coding.class35;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangwei
 */
public class Problem_0412_FizzBuzz {
	// 方法一：模拟法
	public List<String> fizzBuzz(int n) {
		List<String> ans = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			boolean a = i % 3 == 0, b = i % 5 == 0;
			if (a && b) ans.add("FizzBuzz");
			else if (a) ans.add("Fizz");
			else if (b) ans.add("Buzz");
			else ans.add(String.valueOf(i));
		}
		return ans;
	}

	// 方法二：字符串拼接
	public List<String> fizzBuzz2(int n) {
		List<String> ans = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			StringBuilder sb = new StringBuilder();
			if (i % 3 == 0) sb.append("Fizz");
			if (i % 5 == 0) sb.append("Buzz");
			ans.add(sb.length() == 0 ? String.valueOf(i) : sb.toString());
		}
		return ans;
	}

	// 方法三：哈希表
	private static final Map<Integer, String> dict = new HashMap<Integer, String>() {{
		put(3, "Fizz");
		put(5, "Buzz");
	}};
	public static List<String> fizzBuzz3(int n) {
		List<String> ans = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<Integer, String> e : dict.entrySet())
				if (i % e.getKey() == 0) sb.append(e.getValue());
			ans.add(sb.length() == 0 ? String.valueOf(i) : sb.toString());
		}
		return ans;
	}

	public static void main(String[] args) {
		dict.put(7, "Jazz");
		dict.put(11, "Kezz");

		fizzBuzz3(10000).forEach(System.out::println);
		fizzBuzz3(10000).stream().filter(v -> !(v.charAt(0) >= '0' && v.charAt(0) <= '9'))
				.forEach(System.out::println);
	}
}
