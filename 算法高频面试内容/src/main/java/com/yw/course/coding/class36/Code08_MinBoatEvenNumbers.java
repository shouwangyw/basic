package com.yw.course.coding.class36;

import java.util.Arrays;

/**
 * 来自腾讯
 * 给定一个正数数组arr，代表每个人的体重。给定一个正数limit代表船的载重，所有船都是同样的载重量
 * 每个人的体重都一定不大于船的载重
 * 要求：
 * 1, 可以1个人单独一搜船
 * 2, 一艘船如果坐2人，两个人的体重相加需要是偶数，且总体重不能超过船的载重
 * 3, 一艘船最多坐2人
 * 返回如果想所有人同时坐船，船的最小数量
 *
 * @author yangwei
 */
public class Code08_MinBoatEvenNumbers {

	public static int minBoat(int[] arr, int limit) {
		Arrays.sort(arr);
		// 统计奇数、偶数
		int odd = 0, even = 0;
		for (int x : arr) {
			if ((x & 1) == 0) even++;
			else odd++;
		}
		int[] odds = new int[odd], evens = new int[even];
		for (int i = arr.length - 1; i >= 0; i--) {
			if ((arr[i] & 1) == 0) evens[--even] = arr[i];
			else odds[--odd] = arr[i];
		}
		return numRescueBoats(odds, limit) + numRescueBoats(evens, limit);
	}
	private static int numRescueBoats(int[] people, int limit) {
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
		// used: 左右两侧可以两个配对的，noUsed: 左侧不能配对的可以两个配对（除2向上取整），moreUnsolved: 左侧不能配对的单独一船
		return used + ((noUsed + 1) >> 1) + moreUnsolved;
	}
}
