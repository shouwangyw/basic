package com.yw.course.coding.class28;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author yangwei
 */
public class Problem_0020_ValidParentheses {

	private static Map<Character, Character> map = new HashMap<Character, Character>(){{
		put(')', '(');
		put(']', '[');
		put('}', '{');
	}};
	public boolean isValid0(String s) {
		Stack<Character> stack = new Stack<>();
		for (char c : s.toCharArray()) {
			// 遇到左括号: 直接进栈
			if (c == '(' || c == '[' || c == '{') stack.push(c);
				// 遇到右括号: 若有与之配对的左括号，则出栈；否则返回false
			else if (stack.isEmpty() || !stack.pop().equals(map.get(c))) return false;
		}
		// 最后如果栈空，说明括号都能匹配成功
		return stack.isEmpty();
	}

	// 优化
	public boolean isValid(String s) {
		char[] stack = new char[s.length()];
		int size = 0;
		for (char c : s.toCharArray()) {
			// 遇到左括号: 直接进栈
			if (c == '(' || c == '[' || c == '{') stack[size++] = c == '(' ? ')' : (c == '[' ? ']' : '}');
				// 遇到右括号: 若有与之配对的左括号，则出栈；否则返回false
			else if (size == 0 || stack[--size] != c) return false;
		}
		// 最后如果栈空，说明括号都能匹配成功
		return size == 0;
	}

}
