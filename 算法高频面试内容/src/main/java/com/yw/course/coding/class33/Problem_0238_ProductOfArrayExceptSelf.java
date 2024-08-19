package com.yw.course.coding.class33;

/**
 * @author yangwei
 */
public class Problem_0238_ProductOfArrayExceptSelf {

	// 方法一：
	public int[] productExceptSelf1(int[] nums) {
		int n = nums.length;
		int[] l = new int[n], r = new int[n], ans = new int[n];
		l[0] = 1;
		for (int i = 1; i < n; i++) l[i] = l[i - 1] * nums[i - 1];
		r[n - 1] = 1;
		for (int i = n - 2; i >= 0; i--) r[i] = r[i + 1] * nums[i + 1];
		for (int i = 0; i < n; i++) ans[i] = l[i] * r[i];
		return ans;
	}

	// 方法二：
	public int[] productExceptSelf2(int[] nums) {
		int n = nums.length;
		int[] ans = new int[n];
		ans[0] = nums[0];
		for (int i = 1; i < n; i++) ans[i] = ans[i - 1] * nums[i];
		int right = 1;
		for (int i = n - 1; i > 0; i--) {
			ans[i] = ans[i - 1] * right;
			right *= nums[i];
		}
		ans[0] = right;
		return ans;
	}

	// 扩展 : 如果仅仅是不能用除号，把结果直接填在nums里呢？
	// 解法：数一共几个0；每一个位得到结果就是，a / b，位运算替代 /，之前的课讲过（新手班）

}
