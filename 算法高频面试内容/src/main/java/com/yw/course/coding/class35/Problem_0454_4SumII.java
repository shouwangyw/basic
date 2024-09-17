package com.yw.course.coding.class35;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class Problem_0454_4SumII {
//	public static int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
//		HashMap<Integer, Integer> map = new HashMap<>();
//		int sum = 0;
//		for (int i = 0; i < A.length; i++) {
//			for (int j = 0; j < B.length; j++) {
//				sum = A[i] + B[j];
//				if (!map.containsKey(sum)) {
//					map.put(sum, 1);
//				} else {
//					map.put(sum, map.get(sum) + 1);
//				}
//			}
//		}
//		int ans = 0;
//		for (int i = 0; i < C.length; i++) {
//			for (int j = 0; j < D.length; j++) {
//				sum = C[i] + D[j];
//				if (map.containsKey(-sum)) {
//					ans += map.get(-sum);
//				}
//			}
//		}
//		return ans;
//	}

    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        // key: 两数相加的和，value: 累加和数量
        Map<Integer, Integer> map = new HashMap<>();
        for (int x : nums1) {
            for (int y : nums2)
                map.compute(x + y, (k, v) -> v == null ? 1 : v + 1);
        }
        int ans = 0;
        for (int x : nums3) {
            for (int y : nums4)
                ans += map.getOrDefault(-(x + y), 0);
        }
        return ans;
    }

}
