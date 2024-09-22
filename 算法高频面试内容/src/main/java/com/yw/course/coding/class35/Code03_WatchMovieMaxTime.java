package com.yw.course.coding.class35;

import java.util.Arrays;

import static com.yw.util.CommonUtils.swap;

/**
 * 来自小红书
 * 一场电影开始和结束时间可以用一个小数组来表示["07:30","12:00"]
 * 已知有2000场电影开始和结束都在同一天，这一天从00:00开始到23:59结束
 * 一定要选3场完全不冲突的电影来观看，返回最大的观影时间
 * 如果无法选出3场完全不冲突的电影来观看，返回-1
 *
 * @author yangwei
 */
public class Code03_WatchMovieMaxTime {

	// 方法一：暴力解，枚举前三场观看电影的全排列
	public static int maxEnjoy0(int[][] movies) {
		if (movies.length < 3) return -1;
		return process(movies, 0);
	}
	private static int process(int[][] movies, int idx) {
		if (idx == 3) {
			int start = 0, watch = 0;
			for (int i = 0; i < 3; i++) {
				if (start > movies[i][0]) return -1;
				watch += movies[i][1] - movies[i][0];
				start = movies[i][1];
			}
			return watch;
		}
		int ans = -1;
		for (int i = idx; i < movies.length; i++) {
			swap(movies, idx, i);
			ans = Math.max(ans, process(movies, idx + 1));
			swap(movies, idx, i);
		}
		return ans;
	}

	// 方法二：优化后的递归解
	public static int maxEnjoy(int[][] movies) {
		// 先按开始时间从小到大排序，再按结束时间从小到大排序
		Arrays.sort(movies, (a, b) -> a[0] != b[0] ? (a[0] - b[0]) : (a[1] - b[1]));
		return process(movies, 0, 0, 3);
	}
	// 来到第index场电影，此时的时间time，剩余rest场电影
	private static int process(int[][] movies, int idx, int time, int rest) {
		if (idx == movies.length) return rest == 0 ? 0 : -1;
		int p1 = process(movies, idx + 1, time, rest);
		int next = movies[idx][0] >= time && rest > 0 ? process(movies, idx + 1, movies[idx][1], rest - 1) : -1;
		int p2 = next != -1 ? (movies[idx][1] - movies[idx][0] + next) : -1;
		return Math.max(p1, p2);
	}

	// 方法三：记忆化搜索的动态规划
	public static int maxEnjoyByCache(int[][] movies) {
		// 先按开始时间从小到大排序，再按结束时间从小到大排序
		Arrays.sort(movies, (a, b) -> a[0] != b[0] ? (a[0] - b[0]) : (a[1] - b[1]));
		int[][][] cache = new int[movies.length + 1][20][4];
		for (int[][] ca : cache) {
			for (int[] c : ca) Arrays.fill(c, -2);
		}
		return process(movies, 0, 0, 3, cache);
	}
	// 来到第index场电影，此时的时间time，剩余rest场电影
	private static int process(int[][] movies, int idx, int time, int rest, int[][][] cache) {
		if (cache[idx][time][rest] != -2) return cache[idx][time][rest];
		if (idx == movies.length) return cache[idx][time][rest] = rest == 0 ? 0 : -1;
		int p1 = process(movies, idx + 1, time, rest, cache);
		int next = movies[idx][0] >= time && rest > 0 ? process(movies, idx + 1, movies[idx][1], rest - 1, cache) : -1;
		int p2 = next != -1 ? (movies[idx][1] - movies[idx][0] + next) : -1;
		return cache[idx][time][rest] = Math.max(p1, p2);
	}

	public static void main(String[] args) {
		int n = 10;
		int t = 20;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * n) + 1;
			int[][] movies = randomMovies(len, t);
			int ans1 = maxEnjoy0(movies);
//			int ans2 = maxEnjoy(movies);
			int ans2 = maxEnjoyByCache(movies);
			if (ans1 != ans2) {
				for (int[] m : movies) {
					System.out.println(m[0] + " , " + m[1]);
				}
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("出错了");
			}
		}
		System.out.println("测试结束");
	}

	private static int[][] randomMovies(int len, int time) {
		int[][] movies = new int[len][2];
		for (int i = 0; i < len; i++) {
			int a = (int) (Math.random() * time);
			int b = (int) (Math.random() * time);
			movies[i][0] = Math.min(a, b);
			movies[i][1] = Math.max(a, b);
		}
		return movies;
	}

}
