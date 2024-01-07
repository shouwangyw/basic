package com.yw.course.coding.class01;

import com.yw.course.advance.class31.Code01_SegmentTree.SegmentTree;

import java.util.Arrays;

/**
 * 给定两个数组x和hp，长度都是N。
 * x数组一定是有序的，x[i]表示i号怪兽在x轴上的位置；hp数组不要求有序，hp[i]表示i号怪兽的血量
 * 为了方便起见，可以认为x数组和hp数组中没有负数。
 * 再给定一个正数range，表示如果法师释放技能的范围长度, 被打到的每只怪兽损失1点血量。
 * 返回要把所有怪兽血量清空，至少需要释放多少次aoe技能？
 * 三个参数：int[] x, int[] hp, int range
 * 返回：int 次数
 *
 * @author yangwei
 */
public class Code06_AOE {

	// 方法一：纯暴力解法
	public static int minAoe0(int[] x, int[] hp, int range) {
		int n = x.length;
		// cover[i][2]: 表示以i为中心点释放技能，能影响的范围[cover[i][0], cover[i][1]]
		int[][] cover = new int[n][2];
		int left = 0, right = 0;
		for (int i = 0; i < n; i++) {
			while (x[i] - x[left] > range) left++;
			while (right < n && x[right] - x[i] <= range) right++;
			cover[i][0] = left;
			cover[i][1] = right - 1;
		}
		return process(hp, cover);
	}
	private static int process(int[] hp, int[][] cover) {
		int n = hp.length, ans = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			for (int f = cover[i][0]; f <= cover[i][1]; f++) {
				if (hp[f] <= 0) continue;
				int[] next = aoe(hp, cover[i][0], cover[i][1]);
				ans = Math.min(ans, 1 + process(next, cover));
			}
		}
		return ans == Integer.MAX_VALUE ? 0 : ans;
	}
	private static int[] aoe(int[] hp, int l, int r) {
		int n = hp.length;
		int[] next = new int[n];
		for (int i = 0; i < n; i++) next[i] = hp[i];
		for (int i = l; i <= r; i++) next[i] -= next[i] > 0 ? 1 : 0;
		return next;
	}

	// 方法二：贪心
	// 贪心策略：永远让最左边缘以最优的方式(AOE尽可能往右扩，最让最左边缘盖住目前怪的最左)变成0
	// 也就是选择：一定能覆盖到最左边缘, 但是尽量靠右的中心点，等到最左边缘变成0之后，再去找下一个最左边缘
	public static int minAoe(int[] x, int[] hp, int range) {
		int n = x.length, ans = 0;
		for (int i = 0; i < n; i++) {
			if (hp[i] <= 0) continue;
			int triggerPost = i;
			while (triggerPost < n && x[triggerPost] - x[i] <= range) triggerPost++;
			ans += hp[i];
			aoe(x, hp, i, triggerPost - 1, range);
		}
		return ans;
	}
	private static void aoe(int[] x, int[] hp, int l, int trigger, int range) {
		int n = x.length, r = trigger, minus = hp[l];
		while (r < n && x[r] - x[trigger] <= range) r++;
		for (int i = l; i < r; i++) hp[i] = Math.max(0, hp[i] - minus);
	}

	// 方法三：贪心策略和方法二一样，但是需要用线段树，可优化成O(N * logN)
	public static int minAoeOptimal(int[] x, int[] hp, int range) {
		int n = x.length;
		// cover[i][2]: 表示以i为中心点释放技能，能影响的范围[cover[i][0], cover[i][1]]。i下标从1开始，不从0开始
		// 举例：x = [1, 2, 5, 7, 9, 12, 15], range = 3
		// 下标: 	 1  2  3  4  5  6   7
		// 以1位置为中心点，能覆盖位置: 1,2   -> [1,2]
		// 以2位置为中心点，能覆盖位置: 1,2,3 -> [1,3]
		// 以3位置为中心点，能覆盖位置: 2,3,4 -> [2,4]
		// 以4位置为中心点，能覆盖位置: 3,4,5 -> [3,5]
		// 以5位置为中心点，能覆盖位置: 4,5,6 -> [4,6]
		// 以6位置为中心点，能覆盖位置: 5,6,7 -> [5,7]
		// 以7位置为中心点，能覆盖位置: 6,7   -> [6,7]
		// 可以看出如果从左往右，依次求每个位置的左边界(left)和左边界(right)，是可以不回退的！
		int[][] cover = new int[n + 1][2];
		// best[i]: 如果i是最左边缘点，选哪个点作为技能中心点最好，下标从1开始，不从0开始
		int[] best = new int[n + 1];
		int left = 0, right = 0;
		// 从左往右，不回退的依次求每个位置的左边界(left)和左边界(right)
		for (int i = 0; i < n; i++) {
			while (x[i] - x[left] > range) left++;
			while (right < n && x[right] - x[i] <= range) right++;
			cover[i + 1][0] = left + 1;
			cover[i + 1][1] = right;
			best[i + 1] = right;
		}
		// 使用线段树
		SegmentTree st = new SegmentTree(hp);
		st.build(1, n, 1);
		int ans = 0;
		// 整体思路：当前左边缘点从i位置开始(注意0位置已经弃而不用了)，目标是把左边缘的怪物杀死，
		// 但是放技能的位置当然是尽可能远离左边缘点，但是又保证能覆盖住。
		// best[i]: 放技能的位置当然是尽可能远离左边缘点i，但是又保证能覆盖住，请问这个中心在哪？就是best的含义，之前求过了。
		// 然后在这个中心点，放技能，放几次技能呢？左边缘点还剩多少血，就放几次技能，这样能保证刚好杀死左边缘点。
		// 然后向右继续寻找下一个没有死的左边缘点。
		for (int i = 1; i <= n; i++) {
			// 查询当前i位置，还有没有怪物存活
			long leftEdge = st.query(i, i, 1, n, 1);
			// leftEdge <= 0: 没有血了，说明当前边缘点不需要考虑了，换下一个i
			if (leftEdge <= 0) continue;
			// leftEdge > 0: 说明有存活，此时，放技能
			ans += leftEdge;
			// t = best[i]: 在哪放技能最值
			int t = best[i];
			// 在t放技能能影响的左右边界位置
			int l = cover[t][0], r = cover[t][1];
			// 在t放技能leftEdge次，这样左边缘点恰好被杀死，同时[l, r]整个范围，所有的怪物都会扣除掉leftEdge的血量
			st.add(l, r, (int) (-leftEdge), 1, n, 1);
		}
		return ans;
	}

	public static void main(String[] args) {
		int N = 500;
		int X = 10000;
		int H = 50;
		int R = 10;
		int time = 5000;
		System.out.println("test begin");
		for (int i = 0; i < time; i++) {
			int len = (int) (Math.random() * N) + 1;
			int[] x = randomArray(len, X);
			Arrays.sort(x);
			int[] hp = randomArray(len, H);
			int range = (int) (Math.random() * R) + 1;
			int[] x2 = copyArray(x);
			int[] hp2 = copyArray(hp);
			int ans2 = minAoe(x2, hp2, range);
			// 已经测过下面注释掉的内容，注意minAoe0非常慢，
			// 所以想加入对比需要把数据量(N, X, H, R, time)改小
//			int[] x1 = copyArray(x);
//			int[] hp1 = copyArray(hp);
//			int ans1 = minAoe0(x1, hp1, range);
//			if (ans1 != ans2) {
//				System.out.println("Oops!");
//				System.out.println(ans1 + "," + ans2);
//			}
			int[] x3 = copyArray(x);
			int[] hp3 = copyArray(hp);
			int ans3 = minAoeOptimal(x3, hp3, range);
			if (ans2 != ans3) {
				System.out.println("Oops!");
				System.out.println(ans2 + "," + ans3);
			}
		}
		System.out.println("test end");
	}
	private static int[] randomArray(int n, int valueMax) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * valueMax) + 1;
		}
		return ans;
	}
	private static int[] copyArray(int[] arr) {
		int n = arr.length;
		int[] ans = new int[n];
		System.arraycopy(arr, 0, ans, 0, n);
		return ans;
	}
}
