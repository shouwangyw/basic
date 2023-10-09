package com.yw.advance.course.class14;

import java.util.*;

/**
 * 测试链接：https://leetcode.cn/problems/ipo/description/
 * @author yangwei
 */
public class Code04_IPO {

	// 最多K个项目、W是初始资金
	// profits[] capital[] 一定等长
	// 返回最终最大的资金
	public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
		// 重组数据
		int n = profits.length;
		List<int[]> projects = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			projects.add(new int[]{profits[i], capital[i]});
		}
		// 定义小根堆，按capital排序
		Queue<int[]> capitalQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
		// 定义大根堆，按profits排序
		Queue<int[]> profitsQueue = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
		// 所有项目放入capitalQueue
		for (int[] project : projects) capitalQueue.offer(project);
		// 开始做项目
		while (k-- > 0) {
			// 将所有能做的项目弹出，放入profitsQueue
			while (!capitalQueue.isEmpty() && capitalQueue.peek()[1] <= w)
				profitsQueue.add(capitalQueue.poll());
			// 如果profitsQueue为空，说明没可做项目，直接返回
			if (profitsQueue.isEmpty()) return w;
			// 否则，累计收益
			w += profitsQueue.poll()[0];
		}
		return w;
	}
}
