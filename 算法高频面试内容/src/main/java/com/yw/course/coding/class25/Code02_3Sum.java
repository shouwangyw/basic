package com.yw.course.coding.class25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试链接: https://leetcode.cn/problems/3sum/
 *
 * @author yangwei
 */
public class Code02_3Sum {

    // 利用两数之和求三数之和
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int l = i + 1, r = n - 1, k = -nums[i];
            // nums在[i...n-1]范围找到所有二元组相加之和等于k
            while (l < r) {
                int sum = nums[l] + nums[r];
                if (sum == k) {
                    ans.add(Arrays.asList(nums[i], nums[l++], nums[r--]));
                    while (l < n && nums[l] == nums[l - 1]) l++;
                    while (r > l && r < n - 1 && nums[r] == nums[r + 1]) r--;
                } else if (sum < k) l++;
                else r--;
            }
        }
        return ans;
    }

    public static List<List<Integer>> threeSum0(int[] nums) {
        Arrays.sort(nums);
        int N = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = N - 1; i > 1; i--) { // 三元组最后一个数，是arr[i]   之前....二元组 + arr[i]
            if (i == N - 1 || nums[i] != nums[i + 1]) {
                List<List<Integer>> nexts = twoSum(nums, i - 1, -nums[i]);
                for (List<Integer> cur : nexts) {
                    cur.add(nums[i]);
                    ans.add(cur);
                }
            }
        }
        return ans;
    }

    // nums[0...end]这个范围上，有多少个不同二元组，相加==target，全返回
    // {-1,5}     K = 4
    // {1, 3}
    public static List<List<Integer>> twoSum(int[] nums, int end, int target) {
        int L = 0;
        int R = end;
        List<List<Integer>> ans = new ArrayList<>();
        while (L < R) {
            if (nums[L] + nums[R] > target) {
                R--;
            } else if (nums[L] + nums[R] < target) {
                L++;
            } else { // nums[L] + nums[R] == target
                if (L == 0 || nums[L - 1] != nums[L]) {
                    List<Integer> cur = new ArrayList<>();
                    cur.add(nums[L]);
                    cur.add(nums[R]);
                    ans.add(cur);
                }
                L++;
            }
        }
        return ans;
    }

    public static int findPairs(int[] nums, int k) {
        Arrays.sort(nums);
        int left = 0, right = 1;
        int result = 0;
        while (left < nums.length && right < nums.length) {
            if (left == right || nums[right] - nums[left] < k) {
                right++;
            } else if (nums[right] - nums[left] > k) {
                left++;
            } else {
                left++;
                result++;
                while (left < nums.length && nums[left] == nums[left - 1])
                    left++;
            }
        }
        return result;
    }

}
