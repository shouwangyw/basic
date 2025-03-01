package com.yw.course.coding.class38;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author yangwei
 */
public class Problem_0739_DailyTemperatures {

	// 单调栈
	public int[] dailyTemperatures(int[] temperatures) {
		// 定义一个单调栈，存储尚未找到更高温度的索引
		Stack<Integer> descStack = new Stack<>();
		int[] ans = new int[temperatures.length];
		for (int i = 0; i < temperatures.length; i++) {
			// 当前温度比栈顶温度高时，计算栈顶元素的等待天数
			while (!descStack.isEmpty() && temperatures[i] > temperatures[descStack.peek()]) {
				ans[descStack.peek()] = i - descStack.peek();
				descStack.pop();
			}
			descStack.push(i);
		}
		return ans;
	}

}
