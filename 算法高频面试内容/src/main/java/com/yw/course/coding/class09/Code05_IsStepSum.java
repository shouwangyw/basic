package com.yw.course.coding.class09;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class Code05_IsStepSum {

	public static boolean isStepSum(int num) {
		int l = 0, r = num, mid, cur;
		while (l <= r) {
			mid = l + ((r - l) >> 1);
			cur = stepSum(mid);
			if (cur == num) return true;
			else if (cur < num) l = mid + 1;
			else r = mid - 1;
		}
		return false;
	}
	private static int stepSum(int num) {
		int sum = 0;
		while (num > 0) {
			sum += num;
			num /= 10;
		}
		return sum;
	}

	public static void main(String[] args) {
		int max = 1000000;
		int maxStepSum = stepSum(max);
		Map<Integer, Integer> ans = generateStepSumNumberMap(max);
		System.out.println("测试开始");
		for (int i = 0; i <= maxStepSum; i++) {
			if (isStepSum(i) ^ ans.containsKey(i)) {
				System.out.println("出错了！");
			}
		}
		System.out.println("测试结束");
	}
	private static Map<Integer, Integer> generateStepSumNumberMap(int numMax) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i <= numMax; i++) {
			map.put(stepSum(i), i);
		}
		return map;
	}
}
