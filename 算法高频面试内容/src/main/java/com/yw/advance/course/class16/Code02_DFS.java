package com.yw.advance.course.class16;

import com.yw.entity.Vertex;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author yangwei
 */
public class Code02_DFS {

	// 深度优先遍历
	public static void dfs(Vertex start) {
		if (start == null) return;
		Stack<Vertex> stack = new Stack<>();
		Set<Vertex> set = new HashSet<>();
		stack.push(start);
		set.add(start);
		// 入栈即可打印
		System.out.println(start.val);
		while (!stack.isEmpty()) {
			Vertex cur = stack.pop();
			for (Vertex next : cur.nexts) {
				if (set.contains(next)) continue;
				stack.push(cur);
				stack.push(next);
				set.add(next);
				System.out.println(next.val);
				break;
			}
		}
	}
}
