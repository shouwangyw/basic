package com.yw.course.coding.class40;

import com.yw.util.CommonUtils;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 给定int[][] meetings，比如
 * {
 * {66, 70}   0号会议截止时间66，获得收益70
 * {25, 90}   1号会议截止时间25，获得收益90
 * {50, 30}   2号会议截止时间50，获得收益30
 * }
 * 一开始的时间是0，任何会议都持续10的时间，但是一个会议一定要在该会议截止时间之前开始
 * 只有一个会议室，任何会议不能共用会议室，一旦一个会议被正确安排，将获得这个会议的收益
 * 请返回最大的收益
 *
 * @author yangwei
 */
public class Code03_MaxMeetingScore {

	// 方法一：
	public static int maxScore0(int[][] meetings) {
		Arrays.sort(meetings, (o1, o2) -> o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0]);
		int[][] path = new int[meetings.length][];
		return process(meetings, 0, 0, path);
	}
	private static int process(int[][] mettings, int idx, int size, int[][] path) {
		if (idx == mettings.length) {
			int time = 0, ans = 0;
			for (int i = 0; i < size; i++) {
				if (time <= path[i][0]) {
					ans += path[i][1];
					time += 10;
				} else return 0;
			}
			return ans;
		}
		int p1 = process(mettings, idx + 1, size, path);
		path[size] = mettings[idx];
		int p2 = process(mettings, idx + 1, size + 1, path);
		return Math.max(p1, p2);
	}

	// 方法二：小根堆
	public static int maxScore(int[][] meetings) {
		// 按会议截止时间从小到大排序，截止时间相同的按收益从大到小排序
		Arrays.sort(meetings, (o1, o2) -> o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0]);
		// 定义一个小根堆
		Queue<Integer> head = new PriorityQueue<>();
		int time = 0;
		for (int[] m : meetings) {
			if (time <= m[0]) {
				head.offer(m[1]);
				time += 10;
			} else if (!head.isEmpty() && head.peek() < m[1]){
				head.poll();
				head.offer(m[1]);
			}
		}
		int ans = 0;
		while (!head.isEmpty()) ans += head.poll();
		return ans;
	}

	public static void main(String[] args) {
		int n = 12;
		int t = 100;
		int s = 500;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int size = (int) (Math.random() * n) + 1;
			int[][] meetings1 = randomMeetings(size, t, s);
			int[][] meetings2 = copyMeetings(meetings1);
			int ans1 = maxScore0(meetings1);
			int ans2 = maxScore(meetings2);
			if (ans1 != ans2) {
				System.out.println("出错了! ans1 = " + ans1 + ", ans2 = " + ans2);
				CommonUtils.printMatrix(meetings1);
				break;
			}
		}
		System.out.println("测试结束");
	}

	private static int[][] randomMeetings(int n, int t, int s) {
		int[][] ans = new int[n][2];
		for (int i = 0; i < n; i++) {
			ans[i][0] = (int) (Math.random() * t) + 1;
			ans[i][1] = (int) (Math.random() * s) + 1;
		}
		return ans;
	}

	private static int[][] copyMeetings(int[][] meetings) {
		int n = meetings.length;
		int[][] ans = new int[n][2];
		for (int i = 0; i < n; i++) {
			ans[i][0] = meetings[i][0];
			ans[i][1] = meetings[i][1];
		}
		return ans;
	}

}
