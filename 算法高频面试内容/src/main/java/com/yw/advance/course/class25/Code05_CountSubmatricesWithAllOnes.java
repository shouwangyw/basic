package com.yw.advance.course.class25;

/**
 * 测试链接：https://leetcode.cn/problems/count-submatrices-with-all-ones
 * @author yangwei
 */
public class Code05_CountSubmatricesWithAllOnes {

	public static int numSubmat(int[][] mat) {
		if (mat == null || mat.length == 0 || mat[0].length == 0) return 0;
		int ans = 0, m = mat.length, n = mat[0].length;
		int[] heights = new int[n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				heights[j] = mat[i][j] == 0 ? 0 :  heights[j] + 1;
			}
			ans += countFromBottom(heights);
		}
		return ans;
	}

	// 比如
	//              1
	//              1
	//              1         1
	//    1         1         1
	//    1         1         1
	//    1         1         1
	//
	//    2  ....   6   ....  9
	// 如上图，假设在6位置，1的高度为6
	// 在6位置的左边，离6位置最近、且小于高度6的位置是2，2位置的高度是3
	// 在6位置的右边，离6位置最近、且小于高度6的位置是9，9位置的高度是4
	// 此时我们求什么？
	// 1) 求在3~8范围上，必须以高度6作为高的矩形，有几个？
	// 2) 求在3~8范围上，必须以高度5作为高的矩形，有几个？
	// 也就是说，<=4的高度，一律不求
	// 那么，1) 求必须以位置6的高度6作为高的矩形，有几个？
	// 3..3  3..4  3..5  3..6  3..7  3..8
	// 4..4  4..5  4..6  4..7  4..8
	// 5..5  5..6  5..7  5..8
	// 6..6  6..7  6..8
	// 7..7  7..8
	// 8..8
	// 这么多！= 21 = (9 - 2 - 1) * (9 - 2) / 2
	// 这就是任何一个数字从栈里弹出的时候，计算矩形数量的方式
	private static int countFromBottom(int[] heights) {
		int cnt = 0, n = heights.length, si = -1;
		// 自定义数组模拟栈
		int[] stack = new int[n];
		for (int i = 0; i < n; i++) {
			while (si != -1 && heights[stack[si]] >= heights[i]) {
				int j = stack[si--];
				// 考虑重复情况，必须严格大于是才结算，最后一个重复值会算对
				if (heights[j] > heights[i]) {
					int k = si == -1 ? -1 : stack[si], m = i - k - 1;
					int minmax = Math.max(k == -1 ? 0 : heights[k], heights[i]);
					cnt += (heights[j] - minmax) * num(m);
				}
			}
			stack[++si] = i;
		}
		while (si != -1) {
			int j = stack[si--];
			int k = si == -1 ? -1 : stack[si], m = n - k - 1;
			int minmax = k == -1 ? 0 : heights[k];
			cnt += (heights[j] - minmax) * num(m);
		}
		return cnt;
	}
	private static int num(int n) {
		return ((n * (1 + n)) >> 1);
	}

}
