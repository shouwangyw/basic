package com.yw.course.coding.class51;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author yangwei
 */
public class Problem_0630_CourseScheduleIII {

	public int scheduleCourse(int[][] courses) {
		// 按课程截止时间对课程数组进行从小到大排序
		Arrays.sort(courses, Comparator.comparingInt(o -> o[1]));
		// 定义一个花费时间的大根堆
		Queue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
		int time = 0;
		for (int[] course : courses) {
			// 若当前时间+花费 <= 截止时间，则直接入堆
			if (time + course[0] <= course[1]) {
				heap.offer(course[0]);
				time += course[0];
				// 若当前时间+花费 > 截止时间，则考虑淘汰掉某些课（堆顶元素-花费时间更多的被淘汰）
			} else if (!heap.isEmpty() && heap.peek() > course[0]) {
				heap.offer(course[0]);
				time += course[0] - heap.poll();
			}
		}
		return heap.size();
	}

}
