package com.yw.advance.course.class41;

import static com.yw.util.CommonUtils.printArray;

/**
 * 测试链接：https://leetcode.cn/problems/split-array-largest-sum/
 * @author yangwei
 */
public class Code04_SplitArrayLargestSum {

	// 方法一：不优化枚举的动态规划方法，时间复杂度O(N^2 * K)
	public static int splitArray(int[] nums, int k) {
		int n = nums.length;
		int[] sum = getPrefixSum(nums);
		// dp[i][j]: 0~i幅画交给j个画匠完成的最短时间
		int[][] dp = new int[n][k + 1];
		// 初始化dp表，第0列(没有画匠)无效
		// 第0行: 0~0，很显然一幅画，最短时间就是nums[0]
		for (int j = 1; j <= k; j++) dp[0][j] = nums[0];
		// 第1列: 很显然1个画匠完成0~i幅画，最短时间就是arr[0...i]累加和
		for (int i = 1; i < n; i++) dp[i][1] = rangeSum(sum, 0, i);
		// 其它位置: 从上往下、从左往右 递推
		for (int i = 1; i < n; i++) {
			for (int j = 2; j <= k; j++) {
				int ans = Integer.MAX_VALUE;
				// 枚举每一个划分位置(不进行优化)
				for (int leftEnd = 0; leftEnd < i; leftEnd++) {
					ans = Math.min(ans, Math.max(dp[leftEnd][j - 1], rangeSum(sum, leftEnd + 1, i)));
				}
				dp[i][j] = ans;
			}
		}
		return dp[n - 1][k];
	}

	// 方法二：使用四边形不等式优化枚举-动态规划，时间复杂度O(N * K)
	public static int splitArray2(int[] nums, int k) {
		int n = nums.length;
		int[] sum = getPrefixSum(nums);
		// best: 记录取得最优划分点的位置
		int[][] dp = new int[n][k + 1], best = new int[n][k + 1];
		// 初始化dp表，第0列(没有画匠)无效
		// 第0行: 0~0，很显然一幅画，最短时间就是nums[0]，最优划分点不存在 -1
		for (int j = 1; j <= k; j++) {
			dp[0][j] = nums[0];
			best[0][j] = -1;
		}
		// 第1列: 很显然1个画匠完成0~i幅画，最短时间就是arr[0...i]累加和，最优划分点不存在 -1
		for (int i = 1; i < n; i++) {
			dp[i][1] = rangeSum(sum, 0, i);
			best[i][1] = -1;
		}
		// 其它位置: 从左往右、从下往上 递推
		// 为什么这样的顺序？因为要去凑（左，下）优化位置对儿！
		for (int j = 2; j <= k; j++) {
			for (int i = n - 1; i >= 1; i--) {
				int ans = Integer.MAX_VALUE, bestChoose = -1;
				// 如果i==N-1，则不优化上限
				int down = best[i][j - 1], up = i == n - 1 ? n - 1 : best[i + 1][j];
				// 使用四边形不等式优化枚举位置
				for (int leftEnd = down; leftEnd <= up; leftEnd++) {
					int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
					int rightCost = leftEnd == i ? 0 : rangeSum(sum, leftEnd + 1, i);
					int cur = Math.max(leftCost, rightCost);
					/**
					 * 注意下面的if一定是 < 课上的错误就是此处！当时写的 <=
					 * 也就是说，只有取得明显的好处才移动，举个例子来说明，比如[2,6,4,4]，3个画匠时候，如下两种方案都是最优
					 * (2,6) (4) 两个画匠负责 | (4) 最后一个画匠负责；(2,6) (4,4)两个画匠负责 | 最后一个画匠什么也不负责
					 * 第一种方案划分为：[0~2] [3~3]；第二种方案划分为：[0~3] [无]
					 * 两种方案的答案都是8，但是划分点位置一定不要移动，只有明显取得好处时(<)，划分点位置才移动
					 * 也就是说后面的方案如果==前面的最优，不要移动！只有优于前面的最优，才移动
					 * 比如上面的两个方案，如果你移动到了方案二，你会得到：[2,6,4,4] 三个画匠时，最优为[0~3](前两个画家) [无](最后一个画家)，
					 * 最优划分点为3位置(best[3][3])，那么当4个画匠时，也就是求解dp[3][4]时，因为best[3][3] = 3，这个值提供了dp[3][4]的下限
					 * 而事实上dp[3][4]的最优划分为：[0~2]（三个画家处理） [3~3] (一个画家处理)，此时最优解为6
					 * 所以，你就得不到dp[3][4]的最优解了，因为划分点已经越过2了
					 * 提供了对数器验证，你可以改成<=，对数器和leetcode都过不了，这里是<，对数器和leetcode都能通过
					 * 这里面会让同学们感到困惑的点：为啥==的时候，不移动，只有<的时候，才移动呢？例子懂了，但是道理何在？
					 * 哈哈哈哈哈，看了邮局选址问题，你更懵，请看42节！
					 */
					if (cur < ans) {
						ans = cur;
						bestChoose = leftEnd;
					}
				}
				dp[i][j] = ans;
				best[i][j] = bestChoose;
			}
		}
		return dp[n - 1][k];
	}

	// 前缀和数组
	private static int[] getPrefixSum(int[] arr) {
		int n = arr.length;
		int[] sum = new int[n + 1];
		for (int i = 0; i < n; i++) sum[i + 1] = sum[i] + arr[i];
		return sum;
	}
	// 区间和
	private static int rangeSum(int[] sum, int l, int r) {
		return sum[r + 1] - sum[l];
	}

//	// 课上现场写的方法，用了枚举优化，O(N * K)
//	public static int splitArray2(int[] nums, int K) {
//		// 从第2列开始，从左往右
//		// 每一列，从下往上
//		// 为什么这样的顺序？因为要去凑（左，下）优化位置对儿！
//		for (int j = 2; j <= K; j++) {
//			for (int i = N - 1; i >= 1; i--) {
//				int down = best[i][j - 1];
//				// 如果i==N-1，则不优化上限
//				int up = i == N - 1 ? N - 1 : best[i + 1][j];
//				int ans = Integer.MAX_VALUE;
//				int bestChoose = -1;
//				for (int leftEnd = down; leftEnd <= up; leftEnd++) {
//					int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
//					int rightCost = leftEnd == i ? 0 : rangeSum(sum, leftEnd + 1, i);
//					int cur = Math.max(leftCost, rightCost);
//					// 注意下面的if一定是 < 课上的错误就是此处！当时写的 <= ！
//					// 也就是说，只有取得明显的好处才移动！
//					// 举个例子来说明，比如[2,6,4,4]，3个画匠时候，如下两种方案都是最优:
//					// (2,6) (4) 两个画匠负责 | (4) 最后一个画匠负责
//					// (2,6) (4,4)两个画匠负责 | 最后一个画匠什么也不负责
//					// 第一种方案划分为，[0~2] [3~3]
//					// 第二种方案划分为，[0~3] [无]
//					// 两种方案的答案都是8，但是划分点位置一定不要移动!
//					// 只有明显取得好处时(<)，划分点位置才移动!
//					// 也就是说后面的方案如果==前面的最优，不要移动！只有优于前面的最优，才移动
//					// 比如上面的两个方案，如果你移动到了方案二，你会得到:
//					// [2,6,4,4] 三个画匠时，最优为[0~3](前两个画家) [无](最后一个画家)，
//					// 最优划分点为3位置(best[3][3])
//					// 那么当4个画匠时，也就是求解dp[3][4]时
//					// 因为best[3][3] = 3，这个值提供了dp[3][4]的下限
//					// 而事实上dp[3][4]的最优划分为:
//					// [0~2]（三个画家处理） [3~3] (一个画家处理)，此时最优解为6
//					// 所以，你就得不到dp[3][4]的最优解了，因为划分点已经越过2了
//					// 提供了对数器验证，你可以改成<=，对数器和leetcode都过不了
//					// 这里是<，对数器和leetcode都能通过
//					// 这里面会让同学们感到困惑的点：
//					// 为啥==的时候，不移动，只有<的时候，才移动呢？例子懂了，但是道理何在？
//					// 哈哈哈哈哈，看了邮局选址问题，你更懵，请看42节！
//					if (cur < ans) {
//						ans = cur;
//						bestChoose = leftEnd;
//					}
//				}
//				dp[i][j] = ans;
//				best[i][j] = bestChoose;
//			}
//		}
//		return dp[N - 1][K];
//	}

	// 方法三：最优解，O(N) 以画匠数量作为二分的目标
	public static int splitArrayOptimal(int[] nums, int k) {
		// 先计算nums数组的累加和
		long sum = 0;
		for (int x : nums) sum += x;
		// 在0~sum上做二分
		long l = 0, r = sum, mid, cur, ans = 0;
		while (l <= r) {
			mid = (l + r) / 2;
			cur = getNeedParts(nums, mid);
			if (cur <= k) {
				ans = mid;
				r = mid - 1;
			} else l = mid + 1;
		}
		return (int) ans;
	}
	// 达成目标aim时，返回至少需要几个画匠
	private static int getNeedParts(int[] nums, long aim) {
		for (int x : nums) {
			if (x > aim) return Integer.MAX_VALUE;
		}
		int ans = 1, sum = nums[0];
		for (int i = 1; i < nums.length; i++) {
			// 如果超了目标aim，就新加一个画匠，否则累加到sum
			if (sum + nums[i] > aim) {
				ans++;
				sum = nums[i];
			} else sum += nums[i];
		}
		return ans;
	}

	public static void main(String[] args) {
		int N = 100;
		int maxValue = 100;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N) + 1;
			int M = (int) (Math.random() * N) + 1;
			int[] arr = randomArray(len, maxValue);
			int ans1 = splitArray(arr, M);
			int ans2 = splitArray2(arr, M);
			int ans3 = splitArrayOptimal(arr, M);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.print("arr : ");
				printArray(arr);
				System.out.println("M : " + M);
				System.out.println("ans1 : " + ans1);
				System.out.println("ans2 : " + ans2);
				System.out.println("ans3 : " + ans3);
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}

	public static int[] randomArray(int len, int maxValue) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * maxValue);
		}
		return arr;
	}
}
