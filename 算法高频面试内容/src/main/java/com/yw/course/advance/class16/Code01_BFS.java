package com.yw.course.advance.class16;

import com.yw.entity.Vertex;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author yangwei
 */
public class Code01_BFS {

	// 宽度优先遍历
	public static void bfs(Vertex start) {
		if (start == null) return;
		Queue<Vertex> queue = new LinkedList<>();
		Set<Vertex> set = new HashSet<>();
		queue.add(start);
		set.add(start);
		while (!queue.isEmpty()) {
			Vertex cur = queue.poll();
			System.out.println(cur.val);
			for (Vertex next : cur.nexts) {
				if (set.contains(next)) continue;
				queue.add(next);
				set.add(next);
			}
		}
	}
}
