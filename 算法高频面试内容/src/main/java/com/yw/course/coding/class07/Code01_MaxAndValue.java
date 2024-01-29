package com.yw.course.coding.class07;

import com.yw.util.CommonUtils;

import static com.yw.util.CommonUtils.swap;

/**
 * 给定一个正数组成的数组，长度一定大于1，求数组中哪两个数与的结果最大
 *
 * @author yangwei
 */
public class Code01_MaxAndValue {

    // 方法一：暴力解，时间复杂度 O(N^2)
    public static int findMaxAndResult0(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                max = Math.max(max, nums[i] & nums[j]);
            }
        }
        return max;
    }

    // 方法二：时间复杂度 O(N)

    /**
     * 【思路】因为是非负数，所以不用考虑符号位（31位）。首先来到 30位，假设剩余的数字有 N 个(整体)，看看这一位是1的数有几个：
     *
     * ① 有0个或1个：说明不管怎么在数组中选择，任何两个数与(&)的结果在30位上都不可能有1个，最终答案在30位上的状态一定是0，保留剩余的N个数，继续考察第29位，谁也不淘汰（因为谁也不行，干脆接受30位上没有1的事实）；
     * ② 有2个：说明答案就是这两个数，可以直接返回答案，因为别的数在30位上都没有1，就这两个数有。
     * ③ 有大于2个，比如K个：说明答案一定只用在这K个数中取选择某两个数，因为别的数在第30位都没有1，就这K个数有，最终答案的第30位上的状态一定是1。接下来，只要把这K个数作为剩余的数，继续考虑第29位，其它数都淘汰掉
     *
     * ...现在来到第 i 位，假设剩余的数字有 M 个，看看这一位是1的数有几个：
     *
     * ① 有0个或1个：说明不管怎么在这M个数中选择，任何两个数与(&)的结果在第 i 位上都不可能有1个，最终答案在第 i 位上的状态一定是0，保留剩余的M个数，继续考察第 i-1 位；
     * ② 有2个：说明答案就是这两个数，可以直接返回答案，因为别的数在第 i 位上都没有1，就这两个数有。
     * ③ 有大于2个，比如K个：说明答案一定只用在这K个数中取选择某两个数，因为别的数在第 i 位都没有1，就这K个数有，最终答案的第 i 位上的状态一定是1。接下来，只把这K个数作为剩余的数，继续考察第 i-1 位，其它数都淘汰掉。
     */
    public static int findMaxAndResult(int[] nums) {
        int ans = 0, m = nums.length;
        for (int bit = 30; bit >= 0; bit--) {
            // num[0...m-1]、nums[m...]
            int i = 0, tmp = m;
            while (i < m) {
                if ((nums[i] & (1 << bit)) == 0) swap(nums, i, --m);
                else i++;
            }
            // 刚好有2个，说明答案就是这两个数
            if (m == 2) return nums[0] & nums[1];
            if (m < 2) m = tmp;
            // 大于2个数，bit位上有1
            else ans |= (1 << bit);
        }
        return ans;
    }

    public static void main(String[] args) {
        int maxSize = 50;
        int range = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int size = (int) (Math.random() * maxSize) + 2;
            int[] arr = randomArray(size, range);
            int ans1 = findMaxAndResult0(arr);
            int ans2 = findMaxAndResult(arr);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");

    }
    private static int[] randomArray(int size, int range) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * range) + 1;
        }
        return arr;
    }
}
