package com.yw.course.coding.class33;

import java.util.*;

/**
 * @author yangwei
 */
public class Problem_0207_CourseSchedule {

	// 方法一
	public boolean canFinish(int numCourses, int[][] prerequisites) {
		if (prerequisites == null || prerequisites.length == 0) return true;
		Map<Integer, Course> nodes = new HashMap<>();
		for (int[] x : prerequisites) {
			int to = x[0], from = x[1];
			Course t = nodes.compute(to, (k, v) -> v == null ? new Course(to) : v);
			Course f = nodes.compute(from, (k, v) -> v == null ? new Course(from) : v);
			f.nexts.add(t);
			t.in++;
		}
		int needNums = nodes.size();
		Queue<Course> zeroInQueue = new LinkedList<>();
		for (Course node : nodes.values()) {
			if (node.in == 0) zeroInQueue.add(node);
		}
		int cnt = 0;
		while (!zeroInQueue.isEmpty()) {
			Course cur = zeroInQueue.poll();
			cnt++;
			for (Course next : cur.nexts) {
				if (--next.in == 0) zeroInQueue.add(next);
			}
		}
		return cnt == needNums;
	}
	private static class Course {
		private int id; // 课程ID
		private int in; // 入度
		private List<Course> nexts; // 所有邻居课程节点
		public Course(int id) {
			this.id = id;
			this.in = 0;
			this.nexts = new ArrayList<>();
		}
	}

	// 和方法1算法过程一样
	// 但是写法优化了
	public boolean canFinish2(int numCourses, int[][] prerequisites) {
		if (prerequisites == null || prerequisites.length == 0) return true;
		List<List<Integer>> nexts = new ArrayList<>();
		for (int i = 0; i < numCourses; i++) nexts.add(new ArrayList<>());
		int[] in = new int[numCourses];
		for (int[] x : prerequisites) {
			nexts.get(x[1]).add(x[0]);
			in[x[0]]++;
		}
		// 队列
		int[] zero = new int[numCourses];
		int l = 0, r = 0;
		for (int i = 0; i < numCourses; i++) {
			if (in[i] == 0) zero[r++] = i;
		}
		int cnt = 0;
		while (l != r) {
			cnt++;
			for (int next : nexts.get(zero[l++])) {
				if (--in[next] == 0) zero[r++] = next;
			}
		}
		return cnt == nexts.size();
	}

}
