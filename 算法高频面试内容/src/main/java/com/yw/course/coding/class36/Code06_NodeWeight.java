package com.yw.course.coding.class36;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 来自美团
 * 有一棵树，给定头节点h，和结构数组m，下标0弃而不用
 * 比如h = 1, m = [ [] , [2,3], [4], [5,6], [], [], []]
 * 表示1的孩子是2、3; 2的孩子是4; 3的孩子是5、6; 4、5和6是叶节点，都不再有孩子
 * 每一个节点都有颜色，记录在c数组里，比如c[i] = 4, 表示节点i的颜色为4
 * 一开始只有叶节点是有权值的，记录在w数组里，
 * 比如，如果一开始就有w[i] = 3, 表示节点i是叶节点、且权值是3
 * 现在规定非叶节点i的权值计算方式：
 * 根据i的所有直接孩子来计算，假设i的所有直接孩子，颜色只有a,b,k
 * w[i] = Max {
 * 		  (颜色为a的所有孩子个数 + 颜色为a的孩子权值之和),
 * 		  (颜色为b的所有孩子个数 + 颜色为b的孩子权值之和),
 * 		  (颜色为k的所有孩子个数 + 颜色k的孩子权值之和)
 *                }
 * 请计算所有孩子的权值并返回
 *
 * @author yangwei
 */
public class Code06_NodeWeight {

	public static void getNodeWeight(int h, int[][] m, int[] w, int[] c) {
		// m[i]空数组-叶子节点
		if (m[h].length == 0) return;
		// colorCnt: 颜色统计，weightSum: 每种颜色的权重累加统计
		Map<Integer, Integer> colorCnt = new HashMap<>();
		Map<Integer, Integer> weightSum = new HashMap<>();
		for (int x : m[h]) {
			getNodeWeight(x, m, w, c);
			colorCnt.put(c[x], colorCnt.getOrDefault(c[x], 0) + 1);
			weightSum.put(c[x], weightSum.getOrDefault(c[x], 0) + w[x]);
		}
		for (int color : colorCnt.keySet()) {
			w[h] = Math.max(w[h], colorCnt.get(color) + weightSum.get(color));
		}
	}

	public static void main(String[] args) {
		int[][] m = {{} , {2,3}, {4}, {5,6}, {}, {}, {}};
		int[] w = {0, 0, 0, 0, 6, 3, 5};
		int[] c = {0, 1, 2, 3, 4, 5, 6};
		getNodeWeight(1, m, w, c);

		System.out.println(Arrays.toString(w));
	}

}
