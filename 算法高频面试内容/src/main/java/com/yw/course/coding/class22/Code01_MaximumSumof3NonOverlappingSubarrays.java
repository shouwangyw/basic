package com.yw.course.coding.class22;

/**
 * 测试链接: https://leetcode.cn/problems/maximum-sum-of-3-non-overlapping-subarrays/
 *
 * @author yangwei
 */
public class Code01_MaximumSumof3NonOverlappingSubarrays {

//	public static int[] maxSumArray1(int[] arr) {
//		int N = arr.length;
//		int[] help = new int[N];
//		// help[i] 子数组必须以i位置结尾的情况下，累加和最大是多少？
//		help[0] = arr[0];
//		for (int i = 1; i < N; i++) {
//			int p1 = arr[i];
//			int p2 = arr[i] + help[i - 1];
//			help[i] = Math.max(p1, p2);
//		}
//		// dp[i] 在0~i范围上，随意选一个子数组，累加和最大是多少？
//		int[] dp = new int[N];
//		dp[0] = help[0];
//		for (int i = 1; i < N; i++) {
//			int p1 = help[i];
//			int p2 = dp[i - 1];
//			dp[i] = Math.max(p1, p2);
//		}
//		return dp;
//	}
//
//	public static int maxSumLenK(int[] arr, int k) {
//		int N = arr.length;
//		// 子数组必须以i位置的数结尾，长度一定要是K，累加和最大是多少？
//		// help[0] help[k-2] ...
//		int sum = 0;
//		for (int i = 0; i < k - 1; i++) {
//			sum += arr[i];
//		}
//		// 0...k-2 k-1 sum
//		int[] help = new int[N];
//		for (int i = k - 1; i < N; i++) {
//			// 0..k-2 
//			// 01..k-1
//			sum += arr[i];
//			help[i] = sum;
//			// i == k-1  
//			sum -= arr[i - k + 1];
//		}
//		// help[i] - > dp[i]  0-..i  K
//
//	}

    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        // left[i]表示0到i范围，取得长度为k的子数组最大累加和时，子数组的起始位置
        // right[i]表示i到n-1范围，取得长度为k的子数组最大累加和时，子数组的起始位置
        // range[i]表示起始位置是i，长度为k的子数组的累加和
        int[] left = new int[n], right = new int[n], range = new int[n];
        for (int i = 0, sum = 0, max = 0; i < n; i++) {
            if (i < k) {
                sum += nums[i];
                max = sum;
                range[0] = sum;
                continue;
            }
            sum = sum - nums[i - k] + nums[i];
            range[i - k + 1] = sum;
            left[i] = left[i - 1];
            if (sum > max) {
                max = sum;
                left[i] = i - k + 1;
            }
        }
        right[n - k] = n - k;
        for (int i = n - 1, sum = 0, max = 0; i >= 0; i--) {
            if (i > n - k - 1) {
                sum += nums[i];
                max = sum;
                continue;
            }
            sum = sum - nums[i + k] + nums[i];
            right[i] = right[i + 1];
            if (sum >= max) {
                max = sum;
                right[i] = i;
            }
        }
        int[] ans = new int[3];
        for (int i = k, max = 0; i < n - 2 * k + 1; i++) {
            int l = range[left[i - 1]], mid = range[i], r = range[right[i + k]];
            if (l + mid + r > max) {
                max = l + mid + r;
                ans[0] = left[i - 1];
                ans[1] = i;
                ans[2] = right[i + k];
            }
        }
        return ans;
    }

}
