package com.yw.advance.course.class07;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author yangwei
 */
public class Code01_CoverMax {

	/**
	 * 方法一：暴力解法
	 * 依次计算 min+0.5, min+1.5, ..., max-0.5 位置线段重合的数量
	 * 取一个最大值，即为所求答案
	 */
	public static int maxCover(int[][] lines) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < lines.length; i++) {
			min = Math.min(min, lines[i][0]);
			max = Math.max(max, lines[i][1]);
		}
		int cover = 0;
		for (double p = min + 0.5; p < max; p += 1) {
			int cur = 0;
			for (int i = 0; i < lines.length; i++) {
				if (lines[i][0] < p && lines[i][1] > p) {
					cur++;
				}
			}
			cover = Math.max(cover, cur);
		}
		return cover;
	}

	/**
	 * 方法二：基于小根堆
	 * 再依次处理每一条线段:
	 * 		根据当前线段的左边界line[0]，将堆中所有 <= line[0] 的移出
	 * 		并将当前线段的右边界 line[1] 放进堆中去，此时堆中的元素个数就是当前线段的最大重合区域个数
	 * 从所有线段的重合区域选一个最大值即可
	 */
	public static int maxCoverByHeap(int[][] lines) {
		// 排序是非必须的
//		Arrays.sort(lines, Comparator.comparingInt(v -> v[0]));
		Queue<Integer> heap = new PriorityQueue<>();
		int max = 0;
		for (int[] line : lines) {
			while (!heap.isEmpty() && heap.peek() <= line[0]) heap.poll();
			heap.add(line[1]);
			max = Math.max(max, heap.size());
		}
		return max;
	}

	public static int maxCoverByHeap2(int[][] m) {
		Line[] lines = new Line[m.length];
		for (int i = 0; i < m.length; i++) {
			lines[i] = new Line(m[i][0], m[i][1]);
		}
		Arrays.sort(lines, Comparator.comparingInt(o -> o.start));
		// 小根堆，每一条线段的结尾数值，使用默认的
		Queue<Integer> heap = new PriorityQueue<>();
		int max = 0;
		for (int i = 0; i < lines.length; i++) {
			// lines[i] -> cur 在黑盒中，把<=cur.start 东西都弹出
			while (!heap.isEmpty() && heap.peek() <= lines[i].start) heap.poll();
			heap.add(lines[i].end);
			max = Math.max(max, heap.size());
		}
		return max;
	}

	private static class Line {
		int start;
		int end;

		Line(int s, int e) {
			start = s;
			end = e;
		}
	}

	// for test
	public static int[][] generateLines(int N, int L, int R) {
		int size = (int) (Math.random() * N) + 1;
		int[][] ans = new int[size][2];
		for (int i = 0; i < size; i++) {
			int a = L + (int) (Math.random() * (R - L + 1));
			int b = L + (int) (Math.random() * (R - L + 1));
			if (a == b) {
				b = a + 1;
			}
			ans[i][0] = Math.min(a, b);
			ans[i][1] = Math.max(a, b);
		}
		return ans;
	}

	public static void main(String[] args) {

		Line l1 = new Line(4, 9);
		Line l2 = new Line(1, 4);
		Line l3 = new Line(7, 15);
		Line l4 = new Line(2, 4);
		Line l5 = new Line(4, 6);
		Line l6 = new Line(3, 7);

		// 底层堆结构，heap
		PriorityQueue<Line> heap = new PriorityQueue<>(Comparator.comparingInt(o -> o.start));
		heap.add(l1);
		heap.add(l2);
		heap.add(l3);
		heap.add(l4);
		heap.add(l5);
		heap.add(l6);

		while (!heap.isEmpty()) {
			Line cur = heap.poll();
			System.out.println(cur.start + "," + cur.end);
		}

		System.out.println("test begin");
		int N = 100;
		int L = 0;
		int R = 200;
		int testTimes = 200000;
		for (int i = 0; i < testTimes; i++) {
			int[][] lines = generateLines(N, L, R);
			int ans1 = maxCover(lines);
			int ans2 = maxCoverByHeap(lines);
			int ans3 = maxCoverByHeap2(lines);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test end");
	}

}
