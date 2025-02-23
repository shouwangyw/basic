package com.yw.course.coding.class37;

import java.util.*;

/**
 * 来自网易
 * 刚入职网易互娱，新人mini项目便如火如荼的开展起来。为了更好的项目协作与管理，
 * 小易决定将学到的甘特图知识用于mini项目时间预估。小易先把项目中每一项工作以任务的形式列举出来，
 * 每项任务有一个预计花费时间与前置任务表，必须完成了该任务的前置任务才能着手去做该任务。
 * 作为经验PM，小易把任务划分得井井有条，保证没有前置任务或者前置任务全数完成的任务，都可以同时进行。
 * 小易给出了这样一个任务表，请作为程序的你计算需要至少多长时间才能完成所有任务。
 * 输入第一行为一个正整数T，表示数据组数。
 * 对于接下来每组数据，第一行为一个正整数N，表示一共有N项任务。
 * 接下来N行，每行先有两个整数Di和Ki，表示完成第i个任务的预计花费时间为Di天，该任务有Ki个前置任务。
 * 之后为Ki个整数Mj，表示第Mj个任务是第i个任务的前置任务。
 * 数据范围：对于所有数据，满足1<=T<=3, 1<=N, Mj<=100000, 0<=Di<=1000, 0<=sum(Ki)<=N*2。
 *
 * @author yangwei
 */
public class Code01_ArrangeProject {

	public static int maxDays(List<Integer>[] nums, int[] days, int[] headCounts) {
		Queue<Integer> queue = countHead(headCounts);
		int maxDay = 0;
		int[] countDay = new int[days.length];
		while (!queue.isEmpty()) {
			int cur = queue.poll();
			countDay[cur] += days[cur];
			for (int i = 0; i < nums[cur].size(); i++) {
				headCounts[nums[cur].get(i)]--;
				if (headCounts[nums[cur].get(i)] == 0) {
					queue.offer(nums[cur].get(i));
				}
				countDay[nums[cur].get(i)] = Math.max(countDay[nums[cur].get(i)], countDay[cur]);
			}
		}
		for (int i = 0; i < countDay.length; i++) {
			maxDay = Math.max(maxDay, countDay[i]);
		}
		return maxDay;
	}

	private static Queue<Integer> countHead(int[] headCounts) {
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < headCounts.length; i++) {
			if (headCounts[i] == 0)
				queue.offer(i); // 没有前驱任务
		}
		return queue;
	}

	public static void main(String[] args) {
		// 测试用例1：单任务
		List<Integer>[] nums1 = new ArrayList[1];
		nums1[0] = new ArrayList<>();
		int[] days1 = {5};
		int[] heads1 = {0};
		System.out.println(maxDays(nums1, days1, heads1)); // 输出5

		// 测试用例2：链式依赖
		List<Integer>[] nums2 = new ArrayList[3];
		Arrays.setAll(nums2, i -> new ArrayList<>());
		nums2[0].add(1);
		nums2[0].add(2);
		int[] days2 = {2, 3, 4};
		int[] heads2 = {0, 1, 1};
		System.out.println(maxDays(nums2, days2, heads2)); // 输出6

		// 测试用例3：多前置依赖
		List<Integer>[] nums3 = new ArrayList[4];
		Arrays.setAll(nums3, i -> new ArrayList<>());
		nums3[0].add(1);
		nums3[0].add(2);
		nums3[1].add(3);
		nums3[2].add(3);
		int[] days3 = {3, 2, 4, 5};
		int[] heads3 = {0, 1, 1, 2};
		System.out.println(maxDays(nums3, days3, heads3)); // 输出12

		// 测试用例4：独立任务
		List<Integer>[] nums4 = new ArrayList[2];
		Arrays.setAll(nums4, i -> new ArrayList<>());
		int[] days4 = {1, 1};
		int[] heads4 = {0, 0};
		System.out.println(maxDays(nums4, days4, heads4)); // 输出1

		// 测试用例5：复杂路径
		List<Integer>[] nums5 = new ArrayList[4];
		Arrays.setAll(nums5, i -> new ArrayList<>());
		nums5[0].add(1);
		nums5[0].add(2);
		nums5[1].add(3);
		nums5[2].add(3);
		int[] days5 = {1, 2, 3, 4};
		int[] heads5 = {0, 1, 1, 2};
		System.out.println(maxDays(nums5, days5, heads5)); // 输出8
	}

}
