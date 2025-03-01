package com.yw.course.coding.class38;

import java.util.Arrays;

/**
 * @author yangwei
 */
public class Problem_0621_TaskScheduler {

	public int leastInterval(char[] tasks, int n) {
		int[] counter = new int[26]; // 统计每一种任务出现的次数
		for (char task : tasks) counter[task - 'A'] += 1;
		Arrays.sort(counter);
		int m = 0;
		for (int i = 25; i>= 0 && counter[i] == counter[25]; i--, m++) {}
		return Math.max(tasks.length, (counter[25] - 1) * (n + 1) + m);
	}

	// ['A', 'B', 'A']
//	public static int leastInterval(char[] tasks, int free) {
//		int[] count = new int[256];
//		// 出现最多次的任务，到底是出现了几次
//		int maxCount = 0;
//		for (char task : tasks) {
//			count[task]++;
//			maxCount = Math.max(maxCount, count[task]);
//		}
//		// 有多少种任务，都出现最多次
//		int maxKinds = 0;
//		for (int task = 0; task < 256; task++) {
//			if (count[task] == maxCount) {
//				maxKinds++;
//			}
//		}
//		// maxKinds : 有多少种任务，都出现最多次
//		// maxCount : 最多次，是几次？
//		// 砍掉最后一组剩余的任务数
//		int tasksExceptFinalTeam = tasks.length - maxKinds;
//		int spaces = (free + 1) * (maxCount - 1);
//		// 到底几个空格最终会留下！
//		int restSpaces = Math.max(0, spaces - tasksExceptFinalTeam);
//		return tasks.length + restSpaces;
//		// return Math.max(tasks.length, ((n + 1) * (maxCount - 1) + maxKinds));
//	}
	

}
