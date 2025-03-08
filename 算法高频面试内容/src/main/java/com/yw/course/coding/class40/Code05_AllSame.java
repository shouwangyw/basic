package com.yw.course.coding.class40;

import java.util.Map;

/**
 * 来自腾讯
 * 比如arr = {3,1,2,4}
 * 下标对应是：0 1 2 3
 * 你最开始选择一个下标进行操作，一旦最开始确定了是哪个下标，以后都只能在这个下标上进行操作
 * 比如你选定1下标，1下标上面的数字是1，你可以选择变化这个数字，比如你让这个数字变成2
 * 那么arr = {3,2,2,4}
 * 下标对应是：0 1 2 3
 * 因为你最开始确定了1这个下标，所以你以后都只能对这个下标进行操作，
 * 但是，和你此时下标上的数字一样的、且位置连成一片的数字，会跟着一起变
 * 比如你选择让此时下标1的数字2变成3，
 * 那么arr = {3,3,3,4} 可以看到下标1和下标2的数字一起变成3，这是规则！一定会一起变
 * 下标对应是：0 1 2 3
 * 接下来，你还是只能对1下标进行操作，那么数字一样的、且位置连成一片的数字(arr[0~2]这个范围)都会一起变
 * 决定变成4
 * 那么arr = {4,4,4,4}
 * 下标对应是：0 1 2 3
 * 至此，所有数都成一样的了，你在下标1上做了3个决定(第一次变成2，第二次变成3，第三次变成4)，
 * 因为联动规则，arr全刷成一种数字了
 * 给定一个数组arr，最开始选择哪个下标，你随意
 * 你的目的是一定要让arr都成为一种数字，注意联动效果会一直生效
 * 返回最小的变化数
 * arr长度 <= 200, arr中的值 <= 10^6
 *
 * @author yangwei
 */
public class Code05_AllSame {

	public static int allSame(int[] arr) {
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			ans = Math.max(ans, process(arr, i - 1, arr[i], i + 1));
		}
		return ans;
	}

	// 	左部分 	中间部分	    右部分
	// ..left  ...midV...  right..
	// 返回arr都刷成一样的，最小代价是多少
	// left<=n, right<=n, midV<=max(arr)
	public static int process(int[] arr, int left, int midV, int right) {
		// 扩展中间区域的左右边界
		while (left >= 0 && arr[left] == midV) left--;
		while (right < arr.length && arr[right] == midV) right++;
		// left == -1说明没有左边，right == n说明没有右边
		if (left == -1 && right == arr.length) return 0;
		int ans = Integer.MAX_VALUE;
		// 选择合并左区间或右区间，取较小操作次数
		if (left >= 0)
			ans = Math.min(ans, process(arr, left - 1, arr[left], right) + 1);
		if (right < arr.length)
			ans = Math.min(ans, process(arr, left, arr[right], right + 1) + 1);
		return ans;
	}
	
	// 如上的递归，请改动态规划，具体参考体系学习班，动态规划大章节！

}
