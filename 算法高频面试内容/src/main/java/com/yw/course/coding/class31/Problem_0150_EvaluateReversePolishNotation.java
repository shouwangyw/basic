package com.yw.course.coding.class31;

import java.util.Stack;

/**
 * @author yangwei
 */
public class Problem_0150_EvaluateReversePolishNotation {

	public int evalRPN(String[] tokens) {
		Stack<Integer> stack = new Stack<>();
		for (String token : tokens) {
			if (Character.isDigit(token.charAt(token.length() - 1))) stack.push(Integer.valueOf(token));
			else {
				int b = stack.pop(), a = stack.pop();
				switch (token) {
					case "+": stack.push(a + b); break;
					case "-": stack.push(a - b); break;
					case "*": stack.push(a * b); break;
					case "/": stack.push(a / b); break;
				}
			}
		}
		return stack.pop();
	}

}
