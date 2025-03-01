package com.yw.course.coding.class38;

import com.yw.course.advance.class31.Code01_SegmentTree;

import java.util.Map;

/**
 * 360笔试题
 * 长城守卫军
 * 题目描述：
 * 长城上有连成一排的n个烽火台，每个烽火台都有士兵驻守。
 * 第i个烽火台驻守着ai个士兵，相邻峰火台的距离为1。另外，有m位将军，
 * 每位将军可以驻守一个峰火台，每个烽火台可以有多个将军驻守，
 * 将军可以影响所有距离他驻守的峰火台小于等于x的烽火台。
 * 每个烽火台的基础战斗力为士兵数，另外，每个能影响此烽火台的将军都能使这个烽火台的战斗力提升k。
 * 长城的战斗力为所有烽火台的战斗力的最小值。
 * 请问长城的最大战斗力可以是多少？
 * 输入描述
 * 第一行四个正整数n,m,x,k(1<=x<=n<=10^5,0<=m<=10^5,1<=k<=10^5)
 * 第二行n个整数ai(0<=ai<=10^5)
 * 输出描述 仅一行，一个整数，表示长城的最大战斗力
 * 样例输入
 * 5 2 1 2
 * 4 4 2 4 4
 * 样例输出
 * 6
 *
 * @author yangwei
 */
public class Code02_GreatWall {

	// 二分答案+AOE贪心+线段树
	public static int maxForce(int[] wall, int m, int x, int k) {
		long l = 0, r = 0;
		for (int num : wall) r = Math.max(r, num);
		r += m * k;
		long ans = 0;
		while (l <= r) {
			long mid = (l + r) / 2;
			if (verify(wall, m, x, k, mid)) {
				ans = mid;
				l = mid + 1;
			} else r = mid - 1;
		}
		return (int) ans;
	}

	private static boolean verify(int[] wall, int m, int x, int k, long limit) {
		int n = wall.length;
		// 注意：下标从1开始
		Code01_SegmentTree.SegmentTree st = new Code01_SegmentTree.SegmentTree(wall);
		st.build(1, n, 1);
		int need = 0;
		for (int i = 0; i < n; i++) {
			// 注意：下标从1开始
			long cur = st.query(i + 1, i + 1, 1, n, 1);
			if (cur < limit) {
				int add = (int) ((limit - cur + k - 1) / k);
				need += add;
				if (need > m) {
					return false;
				}
				st.add(i + 1, Math.min(i + x, n), add * k, 1, n, 1);
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[] wall = { 3, 1, 1, 1, 3 };
		int m = 2;
		int x = 3;
		int k = 1;
		System.out.println(maxForce(wall, m, x, k));
	}

}
