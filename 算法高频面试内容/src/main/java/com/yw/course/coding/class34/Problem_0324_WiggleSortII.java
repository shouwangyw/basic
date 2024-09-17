package com.yw.course.coding.class34;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Problem_0324_WiggleSortII {

	// 时间复杂度O(N)，额外空间复杂度O(1)
	public static void wiggleSort(int[] nums) {
		if (nums == null || nums.length == 0) return;
		// 假设这个排序是额外空间复杂度O(1)的，当然系统提供的排序并不是，你可以自己实现一个堆排序
		// Arrays.sort(nums);
		int n = nums.length;
		minKth(nums, 0, n - 1, n / 2);
		if ((n & 1) == 0) {
			reverse(nums, 0, n - 1);
			shuffle(nums, 0, n - 1);
		} else shuffle(nums, 1, n - 1);
	}
	// 在[l,r]范围找到第k小的数
	private static int minKth(int[] arr, int l, int r, int k) {
		while (l < r) {
			int pivot = arr[l + (int) (Math.random() * (r - l + 1))];
			int[] equals = partition3Way(arr, l, r, pivot);
			if (k >= equals[0] && k <= equals[1]) return arr[k];
			else if (k < equals[0]) r = equals[0] - 1;
			else l = equals[1] + 1;
		}
		return arr[l];
	}
	// 3路分区
	private static int[] partition3Way(int[] arr, int l, int r, int pivot) {
		int lessR = l - 1, moreL = r + 1;
		while (l < moreL) {
			if (arr[l] < pivot) swap(arr, ++lessR, l++);
			else if (arr[l] > pivot) swap(arr, l, --moreL);
			else l++;
		}
		return new int[] {lessR + 1, moreL - 1};
	}
	private static void swap(int[] arr, int i, int j) {
		if (i == j) return;
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
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

	// 为了测试，暴力方法
	// 把arr全排列尝试一遍，
	// 其中任何一个排列能做到 < > < > ... 返回true;
	// 如果没有任何一个排列能做到，返回false;
	public static boolean test(int[] arr) {
		return process(arr, 0);
	}

	// 为了测试
	public static boolean process(int[] arr, int index) {
		if (index == arr.length) {
			return valid(arr);
		}
		for (int i = index; i < arr.length; i++) {
			swap(arr, index, i);
			if (process(arr, index + 1)) {
				return true;
			}
			swap(arr, index, i);
		}
		return false;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 10;
		int V = 10;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr1 = randomArray(n, V);
			int[] arr2 = copyArray(arr1);
			wiggleSort(arr1);
			if (valid(arr1) != test(arr2)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}
	private static boolean valid(int[] arr) {
		boolean more = true;
		for (int i = 1; i < arr.length; i++) {
			if ((more && arr[i - 1] >= arr[i]) || (!more && arr[i - 1] <= arr[i])) {
				return false;
			}
			more = !more;
		}
		return true;
	}

}
