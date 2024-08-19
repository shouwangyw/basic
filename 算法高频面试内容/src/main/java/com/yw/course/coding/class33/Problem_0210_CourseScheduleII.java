package com.yw.course.coding.class33;

import java.util.*;

/**
 * @author yangwei
 */
public class Problem_0210_CourseScheduleII {

	public int[] findOrder(int numCourses, int[][] prerequisites) {
		int[] ans = new int[numCourses];
		for (int i = 0; i < numCourses; i++) ans[i] = i;
		if (prerequisites == null || prerequisites.length == 0) return ans;
		Map<Integer, Course> nodes = new HashMap<>();
		for (int[] x : prerequisites) {
			int to = x[0], from = x[1];
			Course t = nodes.compute(to, (k ,v) -> v == null ? new Course(to) : v);
			Course f = nodes.compute(from, (k ,v) -> v == null ? new Course(from) : v);
			f.nexts.add(t);
			t.in++;
		}
		int idx = 0;
		Queue<Course> zeroInQueue = new LinkedList<>();
		for (int i = 0; i < numCourses; i++) {
			Course cur = nodes.get(i);
			if (cur == null) ans[idx++] = i;
			else if (cur.in == 0) zeroInQueue.add(cur);
		}
		int needNums = nodes.size(), cnt = 0;
		while (!zeroInQueue.isEmpty()) {
			Course cur = zeroInQueue.poll();
			ans[idx++] = cur.id;
			cnt++;
			for (Course next : cur.nexts) {
				if (--next.in == 0) zeroInQueue.add(next);
			}
		}
		return cnt == needNums ? ans : new int[0];
	}
	private static class Course {
		private int id;
		private int in;
		private List<Course> nexts;
		public Course(int id) {
			this.id = id;
			this.in = 0;
			this.nexts = new ArrayList<>();
		}
	}

}
