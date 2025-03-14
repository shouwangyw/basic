package com.yw.course.coding.class42;

/**
 * @author yangwei
 */
public class Problem_0335_SelfCrossing {

	public static boolean isSelfCrossing(int[] x) {
		if (x == null || x.length < 4) return false;
		// 单独讨论前5次
		if (x[2] <= x[0] && x[3] >= x[1] || x.length > 4 && (x[3] <= x[1] && x[4] >= x[2] || x[3] == x[1] && x[0] + x[4] >= x[2]))
			return true;
		// 之后走统一逻辑
		for (int i = 5; i < x.length; i++) {
			// 判断第i条线发生交叉的可能情况
			if (x[i - 1] <= x[i - 3] && ((x[i] >= x[i - 2]) || (x[i - 2] >= x[i - 4] && x[i - 5] + x[i - 1] >= x[i - 3] && x[i - 4] + x[i] >= x[i - 2])))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		int[] arr = { 2, 2, 3, 2, 2 };
		System.out.println(isSelfCrossing(arr));
	}

}
