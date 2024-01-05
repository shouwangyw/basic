package com.yw.course.advance.class43;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author yangwei
 */
public class Code02_TSP {
	// 方法一：暴力尝试、深度优先遍历
	public static int minTravelDistance0(int[][] matrix) {
		int n = matrix.length;
		// visited[i]: true表示访问过、false表示访问过
		boolean[] visited = new boolean[n];
		return dfs0(matrix, 0, n - 1, visited);
	}
	// 任何两座城市之间的距离，可以在m里面拿到，还剩k座城市没访问
	// 从start出发将剩下没有访问的城市走一遍，最终回到0出发点的最小距离
	private static int dfs0(int[][] m, int start, int k, boolean[] visited) {
		if (k == 0) return m[start][0];
		// 做dfs+回溯
		visited[start] = true;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < m.length; i++) {
			if (visited[i]) continue;
			min = Math.min(min, m[start][i] + dfs0(m, i, k - 1, visited));
		}
		visited[start] = false;
		return min;
	}

	// 方法二：暴力尝试、深度优先遍历，利用一个整型数的位信息替代visited
	public static int minTravelDistance(int[][] matrix) {
		// visited: 位信息上0表示没有访问过，1表示访问过
		int n = matrix.length, visited = 0;
		return dfs(matrix, 0, n - 1, visited);
	}
	private static int dfs(int[][] m, int start, int k, int visited) {
		if (k == 0) return m[start][0];
		// 将第start位置为1，表示访问到
		visited |= (1 << start);
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < m.length; i++) {
			if ((visited & (1 << i)) != 0) continue;
			min = Math.min(min, m[start][i] + dfs(m, i, k - 1, visited));
		}
		// 值传递不用回溯
		// visited &= (~(1 << start));
		return min;
	}

	// 方法三：记忆化搜索
	public static int minTravelDistanceRecord(int[][] matrix) {
		// visited: 位信息上0表示没有访问过，1表示访问过
		int n = matrix.length, visited = 0;
		int[][] record = new int[n][1 << n];
		for (int[] x : record) Arrays.fill(x, -1);
		return dfs(matrix, 0, n - 1, visited, record);
	}
	private static int dfs(int[][] m, int start, int k, int visited, int[][] record) {
		if (record[start][visited] != -1) return record[start][visited];
		if (k == 0) {
			record[start][visited] = m[start][0];
			return record[start][visited];
		}
		// 将第start位置为1，表示访问到
		visited |= (1 << start);
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < m.length; i++) {
			if ((visited & (1 << i)) != 0) continue;
			min = Math.min(min, m[start][i] + dfs(m, i, k - 1, visited, record));
		}
		record[start][visited] = min;
		return record[start][visited];
	}

	// 方法四：动态规划
	public static int minTravelDistanceDp(int[][] matrix) {
		int n = matrix.length, statusNum = 1 << n;
		int[][] dp = new int[n][statusNum];
		for (int status = 0; status < statusNum; status++) {
			for (int start = 0; start < n; start++) {
				if ((status & (1 << start)) == 0) continue;
				if (status == (status & (~status + 1))) {
					dp[start][status] = matrix[start][0];
				} else {
					int min = Integer.MAX_VALUE;
					int preStatus = status & (~(1 << start));
					for (int i = 0; i < n; i++) {
						if ((preStatus & (1 << i)) != 0) {
							min = Math.min(min, matrix[start][i] + dp[i][preStatus]);
						}
					}
					dp[start][status] = min;
				}
			}
		}
		return dp[0][statusNum - 1];
	}

	// matrix[i][j] -> i城市到j城市的距离
	public static int tsp1(int[][] matrix, int origin) {
		if (matrix == null || matrix.length < 2 || origin < 0 || origin >= matrix.length) {
			return 0;
		}
		// 要考虑的集合
		ArrayList<Integer> cities = new ArrayList<>();
		// cities[0] != null 表示0城在集合里
		// cities[i] != null 表示i城在集合里
		for (int i = 0; i < matrix.length; i++) {
			cities.add(1);
		}
		// null,1,1,1,1,1,1
		// origin城不参与集合
		cities.set(origin, null);
		return process(matrix, origin, cities, origin);
	}

	// matrix 所有距离，存在其中
	// origin 固定参数，唯一的目标
	// cities 要考虑的集合，一定不含有origin
	// 当前来到的城市是谁，cur
	public static int process(int[][] matrix, int aim, ArrayList<Integer> cities, int cur) {
		boolean hasCity = false; // 集团中还是否有城市
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i) != null) {
				hasCity = true;
				cities.set(i, null);
				// matrix[cur][i] + f(i, 集团(去掉i) )
				ans = Math.min(ans, matrix[cur][i] + process(matrix, aim, cities, i));
				cities.set(i, 1);
			}
		}
		return hasCity ? ans : matrix[cur][aim];
	}

	// cities 里，一定含有cur这座城
	// 解决的是，集合从cur出发，通过集合里所有的城市，最终来到aim，最短距离
	public static int process2(int[][] matrix, int aim, ArrayList<Integer> cities, int cur) {
		if (cities.size() == 1) {
			return matrix[cur][aim];
		}
		cities.set(cur, null);
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i) != null) {
				int dis = matrix[cur][i] + process2(matrix, aim, cities, i);
				ans = Math.min(ans, dis);
			}
		}
		cities.set(cur, 1);
		return ans;
	}

	public static int tsp2(int[][] matrix, int origin) {
		if (matrix == null || matrix.length < 2 || origin < 0 || origin >= matrix.length) {
			return 0;
		}
		int N = matrix.length - 1; // 除去origin之后是n-1个点
		int S = 1 << N; // 状态数量
		int[][] dp = new int[S][N];
		int icity = 0;
		int kcity = 0;
		for (int i = 0; i < N; i++) {
			icity = i < origin ? i : i + 1;
			// 00000000 i
			dp[0][i] = matrix[icity][origin];
		}
		for (int status = 1; status < S; status++) {
			// 尝试每一种状态 status = 0 0 1 0 0 0 0 0 0
			// 下标 8 7 6 5 4 3 2 1 0
			for (int i = 0; i < N; i++) {
				// i 枚举的出发城市
				dp[status][i] = Integer.MAX_VALUE;
				if ((1 << i & status) != 0) {
					// 如果i这座城是可以枚举的，i = 6 ， i对应的原始城的编号，icity
					icity = i < origin ? i : i + 1;
					for (int k = 0; k < N; k++) { // i 这一步连到的点，k
						if ((1 << k & status) != 0) { // i 这一步可以连到k
							kcity = k < origin ? k : k + 1; // k对应的原始城的编号，kcity
							dp[status][i] = Math.min(dp[status][i], dp[status ^ (1 << i)][k] + matrix[icity][kcity]);
						}
					}
				}
			}
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			icity = i < origin ? i : i + 1;
			ans = Math.min(ans, dp[S - 1][i] + matrix[origin][icity]);
		}
		return ans;
	}

	public static void main(String[] args) {
		int len = 10;
		int value = 100;
		System.out.println("功能测试开始");
		for (int i = 0; i < 2000; i++) {
			int[][] matrix = generateGraph(len, value);
			int origin = (int) (Math.random() * matrix.length);
			int ans1 = minTravelDistance0(matrix);
			int ans2 = minTravelDistance(matrix);
			int ans3 = minTravelDistanceRecord(matrix);
			int ans4 = minTravelDistanceDp(matrix);
			int ans5 = tsp2(matrix, origin);
			if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4 || ans4 != ans5) {
				System.out.println("fuck");
				break;
			}
		}
		System.out.println("功能测试结束");

		len = 22;
		System.out.println("性能测试开始，数据规模 : " + len);
		int[][] matrix = new int[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				matrix[i][j] = (int) (Math.random() * value) + 1;
			}
		}
		for (int i = 0; i < len; i++) {
			matrix[i][i] = 0;
		}
		long start;
		long end;
		start = System.currentTimeMillis();
		minTravelDistanceDp(matrix);
		end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");
	}

	private static int[][] generateGraph(int maxSize, int maxValue) {
		int len = (int) (Math.random() * maxSize) + 1;
		int[][] matrix = new int[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				matrix[i][j] = (int) (Math.random() * maxValue) + 1;
			}
		}
		for (int i = 0; i < len; i++) {
			matrix[i][i] = 0;
		}
		return matrix;
	}
}
