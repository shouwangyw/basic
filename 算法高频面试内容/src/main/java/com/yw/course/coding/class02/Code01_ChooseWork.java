package com.yw.course.coding.class02;

import java.util.Arrays;
import java.util.TreeMap;

/**
 * 测试链接：https://leetcode.cn/problems/most-profit-assigning-work/
 * @author yangwei
 */
public class Code01_ChooseWork {

	public int[] maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
		// 将difficulty、profit封装成工作
		int[][] job = new int[difficulty.length][2];
		for (int i = 0; i < difficulty.length; i++) {
			job[i][0] = difficulty[i];
			job[i][1] = profit[i];
		}
		// 对job排序: 先难度升序，难度相同按收益降序
		Arrays.sort(job, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]);
		// 定义一个有序表，key: 难度，value: 收益
		TreeMap<Integer, Integer> map = new TreeMap<>();
		map.put(0, 0); // 避免边界值判断
		// preJob: 上一份进map的工作，至少job[0]能进map
		int[] preJob = job[0], curJob;
		map.put(preJob[0], preJob[1]);
		for (int i = 1; i < job.length; i++) {
			curJob = job[i];
			// 过滤掉难度相同但收入低的，以及难度更大但收入没增加的
			if (curJob[0] == preJob[0] || curJob[1] <= preJob[1]) continue;
			map.put(curJob[0], curJob[1]);
			preJob = curJob;
		}
		// LeetCode826 改成ans的累加和即可
		int[] ans = new int[worker.length];
		for (int i = 0; i < worker.length; i++) {
			// 当前工人能力 <= worker[i]，且离它最近的
			ans[i] = map.get(map.floorKey(worker[i]));
		}
		return ans;
	}
}
