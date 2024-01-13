package com.yw.course.coding.class03;

import java.util.Arrays;

/**
 * 给定一个正数数组arr，代表若干人的体重，再给定一个正数limit，表示所有船共同拥有的载重量
 * 每艘船最多坐两人，且不能超过载重，想让所有的人同时过河，并且用最好的分配方法让船尽量少
 * 返回最少的船数
 * 测试链接 : https://leetcode.cn/problems/boats-to-save-people/
 * @author yangwei
 */
public class Code05_BoatsToSavePeople {

	public int numRescueBoats(int[] people, int limit) {
		// 对人的体重排序
		Arrays.sort(people);
		int n = people.length, lessR = -1, half = limit >> 1;
		// 如果体重最大的超过limit，无解
		if (people[n - 1] > limit) return -1;
		// 否则，如果体重最小的超过limit一半，那么每艘船最多一人，需要n艘船
		if (people[0] > half) return n;
		// 再否则，找到小于等于 limit/2 的位置作为分界
		for (int i = n - 1; i >= 0; i--) {
			if (people[i] <= half) { lessR = i; break; }
		}
		// 定义左右两个指针，寻找两两配对的可能
		int l = lessR, r = lessR + 1, noUsed = 0;
		while (l >= 0) {
			int solved = 0;
			while (r < n && people[l] + people[r] <= limit) { r++; solved++; }
			if (solved == 0) { l--; noUsed++; }
			else l = Math.max(-1, l - solved);
		}
		// 1 1 1 1 1 2 3 5 5 5 5 | 6 6 8 8 9 10   limit = 10
		//                     ↑   ↑
		//                     l   r
		// 1 1 1 1 1 2 3 5 5 5 5 | 6 6 8 8 9 10
		//             ↑ × × × ×   ↑
		//             l           r
		// 1 1 1 1 1 2 3 5 5 5 5 | 6 6 8 8 9 10
		//   ↑ √ √ √ √ √ × × × ×   √ √ √ √ √ ↑
		//   l                               r
		//  1 1 1 1 1 2 3 5 5 5 5 | 6 6 8 8 9 10
		//↑ × × √ √ √ √ √ × × × ×   √ √ √ √ √ ↑
		//l                                   r
		// x: noUsed, √: used, 10: moreUnsolved
		int all = lessR + 1, used = all - noUsed, moreUnsolved = (n - all) - used;
		return used + ((noUsed + 1) >> 1) + moreUnsolved;
	}

}
