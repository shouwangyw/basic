package com.yw.course.advance.class40;

/**
 * @author yangwei
 */
public class Code03_LongestLessSumSubArrayLength {

	public static int maxLengthSubArraySum(int[] arr, int k) {
		if (arr == null || arr.length == 0) return 0;
		int n = arr.length;
		// minSums[i]: 子数组以i为开始位置的最小累加和
		// minSumEnds[i]: 以i为开始位置的子数组取得最小累加和时的结束位置
		int[] minSums = new int[n], minSumEnds = new int[n];
		minSums[n - 1] = arr[n - 1];
		minSumEnds[n - 1] = n - 1;
		for (int i = n - 2; i >= 0; i--) {
			if (minSums[i + 1] < 0) {
				minSums[i] = arr[i] + minSums[i + 1];
				minSumEnds[i] = minSumEnds[i + 1];
			} else {
				minSums[i] = arr[i];
				minSumEnds[i] = i;
			}
		}
		// i: 窗口的最左位置，end是扩出来有效的最后位置的下一个位置，窗口: [i, end)
		// sum: 窗口内子数组累加和
		int end = 0, sum = 0, len = 0;
		for (int i = 0; i < n; i++) {
			// 若end还能扩，且将【以end为开始位置的最小累加和】加进来没超过k
			// 则将这一块子数组累加和加进来，end来到【以end为开始位置的最小累加和的结束位置】的下一个位置，继续..
			while (end < n && sum + minSums[end] <= k) {
				sum += minSums[end];
				end = minSumEnds[end] + 1;
			}
			// while循环出来后，更新答案 len
			len = Math.max(len, end - i);
			// 还有窗口，哪怕窗口没有数字 [i~end) [4,4)
			if (end > i) sum -= arr[i];
			// i==end, 即将i++, i>end, 此时窗口维持不住了，所以end跟着i一起走
			else end = i + 1;
		}
		return len;
	}

	public static int maxLength(int[] arr, int k) {
		int[] h = new int[arr.length + 1];
		int sum = 0;
		h[0] = sum;
		for (int i = 0; i != arr.length; i++) {
			sum += arr[i];
			h[i + 1] = Math.max(sum, h[i]);
		}
		sum = 0;
		int res = 0;
		int pre = 0;
		int len = 0;
		for (int i = 0; i != arr.length; i++) {
			sum += arr[i];
			pre = getLessIndex(h, sum - k);
			len = pre == -1 ? 0 : i - pre + 1;
			res = Math.max(res, len);
		}
		return res;
	}

	public static int getLessIndex(int[] arr, int num) {
		int low = 0;
		int high = arr.length - 1;
		int mid = 0;
		int res = -1;
		while (low <= high) {
			mid = (low + high) / 2;
			if (arr[mid] >= num) {
				res = mid;
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println("test begin");
		for (int i = 0; i < 10000000; i++) {
			int[] arr = generateRandomArray(10, 20);
			int k = (int) (Math.random() * 20) - 5;
			if (maxLengthSubArraySum(arr, k) != maxLength(arr, k)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");
	}

	// for test
	public static int[] generateRandomArray(int len, int maxValue) {
		int[] res = new int[len];
		for (int i = 0; i != res.length; i++) {
			res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
		}
		return res;
	}
}
