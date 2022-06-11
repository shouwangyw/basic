package com.yw.basic.algorithm;

import java.util.*;

/**
 * @author yangwei
 * @date 2021-09-11 14:48
 */
public class Solution {
    private int ans = 0;
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        int[] sum = new int[n + 1];
        for (int i = 0; i < n; i++) sum[i + 1] = sum[i] + nums[i];
        Map<Integer, Integer> mapping = new HashMap<>();
        for (int i = 0; i <= n; i++) {
            mapping.compute(sum[i] - k, (key, v) -> {
                if (v != null) { ans += v; v += 1; }
                else v = 1;
                return v;
            });
        }
        return ans;
    }
}
