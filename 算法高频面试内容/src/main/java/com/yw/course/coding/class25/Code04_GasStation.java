package com.yw.course.coding.class25;

/**
 * 测试链接 : https://leetcode.cn/problems/gas-station/
 * @author yangwei
 */
// 注意本题的实现比leetcode上的问法更加通用
// leetcode只让返回其中一个良好出发点的位置
// 本题是返回结果数组，每一个出发点是否是良好出发点都求出来了
// 得到结果数组的过程，时间复杂度O(N)，额外空间复杂度O(1)
public class Code04_GasStation {

	public int canCompleteCircuit(int[] gas, int[] cost) {
		if (gas == null || gas.length == 0) return -1;
		if (gas.length == 1) return gas[0] < cost[0] ? -1 : 0;
		boolean[] goods = goodStations(gas, cost);
		for (int i = 0; i < gas.length; i++) {
			if (goods != null && goods[i]) return i;
		}
		return -1;
	}
	private static boolean[] goodStations(int[] gas, int[] cost) {
		if (cost == null || gas == null || cost.length < 2 || cost.length != gas.length) return null;
		int init = changeDisArrGetInit(cost, gas);
		return init == -1 ? null : enlargeArea(cost, init);
	}
	private static int changeDisArrGetInit(int[] dis, int[] oil) {
		int init = -1;
		for (int i = 0; i < dis.length; i++) {
			dis[i] = oil[i] - dis[i];
			if (dis[i] >= 0) init = i;
		}
		return init;
	}
	private static boolean[] enlargeArea(int[] dis, int init) {
		int n = dis.length, start = init, end = nextIdx(init, n), need = 0, rest = 0;
		boolean[] ans = new boolean[n];
		do {
			// 当前来到的start已经在连通区域中，可以确定后续的开始点一定无法转完一圈
			if (start != init && start == lastIdx(end, n)) break;
			// 当前来到的start不在连通区域中，就扩充连通区域
			// start(5) ->  联通区的头部(7) -> 2
			// start(-2) -> 联通区的头部(7) -> 9
			if (dis[start] < need) need -= dis[start]; // 当前start无法接到连通区的头部
			else { // 当前start可以接到连通区的头部，开始扩充连通区域的尾巴
				// start(7) -> 联通区的头部(5)
				rest += dis[start] - need;
				need = 0;
				while (rest >= 0 && end != start) {
					rest += dis[end];
					end = nextIdx(end, n);
				}
				// 如果连通区域已经覆盖整个环，当前的start是良好出发点，进入2阶段
				if (rest >= 0) {
					ans[start] = true;
					connectGood(dis, lastIdx(start, n), init, ans);
					break;
				}
			}
			start = lastIdx(start, n);
		} while (start != init);
		return ans;
	}
	// 已知start的next方向上有一个良好出发点
	// start如果可以达到这个良好出发点，那么从start出发一定可以转一圈
	private static void connectGood(int[] dis, int start, int init, boolean[] ans) {
		int need = 0;
		while (start != init) {
			if (dis[start] < need) need -= dis[start];
			else {
				ans[start] = true;
				need = 0;
			}
			start = lastIdx(start, dis.length);
		}
	}
	private static int lastIdx(int idx, int n) {
		return idx == 0 ? n - 1 : idx - 1;
	}
	private static int nextIdx(int idx, int n) {
		return idx == n - 1 ? 0 : idx + 1;
	}
}
