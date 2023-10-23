package com.yw.advance.course.class23;

/**
 * @author yangwei
 */
public class Code03_NQueens {

	// 方法一
	public static int queenNum0(int n) {
		if (n < 1) return 0;
		return process(0, new int[n], n);
	}
	// 当前来到第i行，共0~n-1行，在i行上放皇后，所有列尝试，必须要保证跟之前所有的皇后不打架
	// int[] record: record[x] = y 表示第x行的皇后，放在了第y列
	// 返回：不关心i以上发生了什么，i...后续有多少合法的方法数
	private static int process(int i, int[] record, int n) {
		if (i == n) return 1;
		int ans = 0;
		// 第i行的皇后，放在哪一列？
		for (int j = 0; j < n; j++) {
			if (isValid(record, i, j)) {
				record[i] = j;
				ans += process(i + 1, record, n);
			}
		}
		return ans;
	}
	private static boolean isValid(int[] record, int i, int j) {
		for (int k = 0; k < i; k++) {
			// 共列 或 共斜线
			if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
				return false;
			}
		}
		return true;
	}

	// 方法二：利用位运算优化
	// 请不要超过32皇后问题
	public static int queenNum(int n) {
		if (n < 1 || n > 32) {
			return 0;
		}
		// 如果你是13皇后问题，limit 最右13个1，其他都是0
		int limit = n == 32 ? -1 : (1 << n) - 1;
		return process(limit, 0, 0, 0);
	}
	// 7皇后问题
	// limit : 0....0 1 1 1 1 1 1 1
	// 之前皇后的列影响：colLim
	// 之前皇后的左下对角线影响：leftDiaLim
	// 之前皇后的右下对角线影响：rightDiaLim
	private static int process(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
		if (colLim == limit) return 1;
		// pos中所有是1的位置，是你可以去尝试皇后的位置
		int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
		int mostRightOne;
		int ans = 0;
		// 尝试所有可以放皇后的位置(获取所有1位置进行尝试)
		while (pos != 0) {
			// pos & (~pos + 1): 提取最右侧的1
			mostRightOne = pos & (~pos + 1);
			pos = pos - mostRightOne;
			ans += process(limit, colLim | mostRightOne, (leftDiaLim | mostRightOne) << 1,
					(rightDiaLim | mostRightOne) >>> 1);
		}
		return ans;
	}

	public static void main(String[] args) {
		int n = 14;

		long start = System.currentTimeMillis();
		System.out.println(queenNum(n));
		long end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + "ms");

		start = System.currentTimeMillis();
		System.out.println(queenNum0(n));
		end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + "ms");

	}
}
