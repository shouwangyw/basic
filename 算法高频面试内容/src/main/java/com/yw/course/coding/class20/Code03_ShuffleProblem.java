package com.yw.course.coding.class20;

import java.util.Arrays;
import java.util.Comparator;

import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code03_ShuffleProblem {

	// 主函数
	public static void shuffle(int[] arr) {
		// 数组必须不为空，且长度为偶数
		if (arr == null || arr.length == 0 || (arr.length & 1) != 0) return;
		shuffle(arr, 0, arr.length - 1);
	}
	// arr[l...r]上做完美洗牌(arr[L..R]范围上一定要是偶数个数字)
	private static void shuffle(int[] arr, int l, int r) {
		while (r - l + 1 > 0) {
			// 切成一块一块的解决，每一块的长度满足(3^k)-1
			int n = r - l + 1, base = 3, k = 1;
			// 计算小于等于 n 且离 n 最近的，满足(3^k)-1的数，也就是找到最大的k，满足3^k <= n+1
			while (base <= (n + 1) / 3) {
				base *= 3;
				k++;
			}
			// 当前要解决长度为base-1的块，一半就是再除2
			int half = (base - 1) / 2, mid = (l + r) / 2;
			// 要旋转的左部分为[l+half...mid], 右部分为[mid+1..mid+half]
			// 注意在这里，arr下标是从0开始的
			rotate(arr, l + half, mid, mid + half);
			// 旋转完成后，从l开始算起，长度为base-1的部分进行下标连续推
			cycles(arr, l, base - 1, k);
			// 解决了前base-1的部分，剩下的部分继续处理: l -> [] [+1...R]
			l = l + base - 1;
		}
	}
	// [l...mid]为左部分，[mid+1...r]为右部分，左右两部分互换
	private static void rotate(int[] arr, int l, int mid, int r) {
		reverse(arr, l, mid);
		reverse(arr, mid + 1, r);
		reverse(arr, l, r);
	}
	// 数组arr在[l..r]范围做逆序调整
	private static void reverse(int[] arr, int l, int r) {
		while (l < r) {
			int tmp = arr[l];
			arr[l++] = arr[r];
			arr[r--] = tmp;
		}
	}
	// 从start位置开始，往右n的长度这一段，做下标连续推，出发位置依次为1,3,9...
	private static void cycles(int[] arr, int start, int n, int k) {
		// 找到每一个出发位置trigger，一共k个，每一个trigger都进行下标连续推
		// 出发位置是从1开始算的，而数组下标是从0开始算的
		for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
			int preVal = arr[trigger + start - 1], curIdx = idx(trigger, n);
			while (curIdx != trigger) {
				int tmp = arr[curIdx + start - 1];
				arr[curIdx + start - 1] = preVal;
				preVal = tmp;
				curIdx = idx(curIdx, n);
			}
			arr[curIdx + start - 1] = preVal;
		}
	}
	// 数组长度n，调整前位置是i，返回调整之后的位置，i从1开始
	private static int idx(int i, int n) {
//		return i <= n / 2 ? 2 * i : (2 * i - n - 1);
		return (2 * i) % (n + 1);
	}

	public static void wiggleSort(int[] nums) {
		if (nums == null || nums.length == 0) return;
		// 假设这个排序是额外空间复杂度O(1)的，当然系统提供的排序并不是，你可以自己实现一个堆排序
		Arrays.sort(nums);
		int n = nums.length;
		if ((n & 1) == 0) {
			reverse(nums, 0, n - 1);
			shuffle(nums, 0, n - 1);
		} else shuffle(nums, 1, n - 1);
	}

	// for test
	public static void main(String[] args) {
		int[] nums = {4,5,5,6};
//		int[] nums = {1,5,1,1,6,4};
		Arrays.sort(nums);
		shuffle(nums);
		System.out.println(Arrays.toString(nums));
		wiggleSort(nums);
		System.out.println(Arrays.toString(nums));
		System.out.println(isValidWiggle(nums));

		for (int i = 0; i < 5000000; i++) {
			int[] arr = generateArray();
			wiggleSort(arr);
			if (!isValidWiggle(arr)) {
				System.out.println("ooops!");
				printArray(arr);
				break;
			}
		}
	}
	private static int[] generateArray() {
		int len = (int) (Math.random() * 10) * 2;
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * 100);
		}
		return arr;
	}
	private static boolean isValidWiggle(int[] arr) {
		for (int i = 1; i < arr.length; i++) {
			if ((i & 1) == 1 && arr[i] < arr[i - 1]) {
				return false;
			}
			if ((i & 1) == 0 && arr[i] > arr[i - 1]) {
				return false;
			}
		}
		return true;
	}
}
