package com.yw.course.coding.class42;

/**
 * @author yangwei
 */
public class Problem_0296_BestMeetingPoint {

	public static int minTotalDistance(int[][] grid) {
		int m = grid.length, n = grid[0].length;
		// 遍历整个二维数组，统计每一行、每一列出现1的个数
		int[] rowOnes = new int[m], colOnes = new int[n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 0) continue;
				rowOnes[i]++;
				colOnes[j]++;
			}
		}
		// 找到最优行
		int total = 0, i = 0, j = m - 1, iRest = 0, jRest = 0;
		while (i < j) {
			if (rowOnes[i] + iRest <= rowOnes[j] + jRest) {
				total += rowOnes[i] + iRest;
				iRest += rowOnes[i++];
			} else {
				total += rowOnes[j] + jRest;
				jRest += rowOnes[j--];
			}
		}
		// 找到最优列
		i = 0;
		j = n - 1;
		iRest = 0;
		jRest = 0;
		while (i < j) {
			if (colOnes[i] + iRest <= colOnes[j] + jRest) {
				total += colOnes[i] + iRest;
				iRest += colOnes[i++];
			} else {
				total += colOnes[j] + jRest;
				jRest += colOnes[j--];
			}
		}
		return total;
	}
}
