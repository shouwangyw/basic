package com.yw.course.coding.class13;

/**
 * 测试链接 : https://leetcode.cn/problems/super-washing-machines/
 * @author yangwei
 */
public class Code02_SuperWashingMachines {

	// 1. 考虑每一个 i 位置最少的操作步数，与平均值比较，分情况讨论
	//    1) i的左侧少了a件、i的右侧多了b件，i位置最少操作步数 Math.max(a, b)
	//    2) i的左侧多了a件、i的右侧少了b件，i位置最少操作步数 Math.max(a, b)
	//    3) i的左侧多了a件、i的右侧多了b件，i位置最少操作步数 Math.max(a, b)
	//    4) i的左侧少了a件、i的右侧少了b件，i位置最少操作步数 a + b
	// 2. 从中决策出一个最大值（短板），就是整体的最少操作步数——结论，证明比较麻烦
	public int findMinMoves(int[] machines) {
		if (machines == null || machines.length == 0) return 0;
		int n = machines.length, sum = 0, leftSum = 0, ans = 0;
		for (int x : machines) sum += x;
		if (sum % n != 0) return -1;
		int avg = sum / n;
		for (int i = 0 ; i < n; i++) {
			int leftRest = leftSum - i * avg, rightRest = (sum - leftSum - machines[i]) - (n - i - 1) * avg;
			if (leftRest < 0 && rightRest < 0)
				ans = Math.max(ans, Math.abs(leftRest) + Math.abs(rightRest));
			else ans = Math.max(ans, Math.max(Math.abs(leftRest), Math.abs(rightRest)));
			leftSum += machines[i];
		}
		return ans;
	}
}
