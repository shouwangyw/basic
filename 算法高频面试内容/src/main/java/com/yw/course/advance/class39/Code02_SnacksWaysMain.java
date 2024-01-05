//不要拷贝包信息的内容
package com.yw.course.advance.class39;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * 测试链接：https://www.nowcoder.com/questionTerminal/d94bb2fa461d42bcb4c0f2b94f5d4281
 * @author yangwei
 */
public class Code02_SnacksWaysMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(), bag = sc.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
		System.out.println(ways(arr, bag));
		sc.close();
	}
	// 方法一：
	private static long ways0(int[] arr, int bag) {
		if (arr == null || arr.length == 0) return 0;
		int n = arr.length, sum = 0;
		for (int x : arr) sum += x;
		sum = Math.min(sum, bag);

//		return process(arr, bag, n - 1, sum);

		long[][] dp = new long[n][sum + 1];
		for (int j = 0; j <= sum; j++) dp[0][j] = 2;
		for (int i = 1; i < n; i++) {
			for (int j = 0; j <= sum; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j >= arr[i]) dp[i][j] += dp[i - 1][j - arr[i]];
			}
		}
		return dp[n - 1][sum];
	}
	private static long process(int[] arr, int bag, int idx, long sum) {
		if (sum > bag) return 0;
		if (idx == 0) return 2;

		return process(arr, bag, idx - 1, sum)
				+ (sum >= arr[idx] ? process(arr, bag, idx - 1, sum - arr[idx]) : 0);
	}
	// 方法二：
	private static long ways(int[] arr, int bag) {
		if (arr == null || arr.length == 0) return 0;
		int n = arr.length;
		if (n == 1) return arr[0] <= bag ? 2 : 1;
		int mid = (n - 1) >> 1;
		Map<Long, Long> leftMap = new TreeMap<>(), rightMap = new TreeMap<>();
		long ways = process(arr, 0, mid, 0, bag, leftMap);
		ways += process(arr, mid + 1, n - 1, 0, bag, rightMap);
		// 转化rightMap: {1:3, 2: 2, 4: 6} -> {1:3, 2: 5, 4: 11} (value是前缀和)
		TreeMap<Long, Long> rightPreMap = new TreeMap<>();
		long pre = 0;
		for (Entry<Long, Long> entry : rightMap.entrySet()) {
			pre += entry.getValue();
			rightPreMap.put(entry.getKey(), pre);
		}
		for (Entry<Long, Long> entry : leftMap.entrySet()) {
			Long rightFloor = rightPreMap.floorKey(bag - entry.getKey());
			if (rightFloor == null) continue;
			ways += entry.getValue() * rightPreMap.get(rightFloor);
		}
		// +1: 什么也不拿的单独算
		return ways + 1;
	}
	/**
	 * 从idx出发，到end结束，之前的选择已经形成的累加和sum
	 * 零食在[idx, end]范围自由选择，所有累加和不能超过bag，每一种累加和对应的方法数放入map里
	 * 比如:[3,3,3,3] bag = 6
	 * 		0 1 2 3			map
	 * 		× × × × 0	->	{0: 1}
	 * 		× × × √ 3 	-> 	{0: 1}{3: 1}
	 * 		× × √ × 3 	-> 	{0: 1}{3: 2}
	 */
	private static long process(int[] arr, int idx, int end, long sum, long bag, Map<Long, Long> map) {
		if (sum > bag) return 0;
		// sum <= bag
		// idx > end : 所有商品自由选择完了
		if(idx > end) {
			// 最后不能什么都不选
			if (sum == 0) return 0;
			map.compute(sum, (k, v) -> v == null ? 1 : v + 1);
			return 1;
		}
		// idx <= end : 还有货
		// 不要当前idx位置的货 + 要当前index位置的货
		return process(arr, idx + 1, end, sum, bag, map) +
				process(arr, idx + 1, end, sum + arr[idx], bag, map);
	}
}