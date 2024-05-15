package com.yw.course.coding.class23;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author yangwei
 */
public class Code04_FindKMajority {

	public static void printHalfMajor(int[] arr) {
		int ans = 0, hp = 0, n = arr.length;
		for (int i = 0; i < n; i++) {
			if (hp == 0) {
				ans = arr[i];
				hp = 1;
			} else if (arr[i] == ans) hp++;
			else hp--;
		}
		if (hp == 0) {
			System.out.println("no such number.");
			return;
		}
		hp = 0;
		for (int x : arr) if (x == ans) hp++;
		System.out.println(hp > n / 2 ? ans : "no such number.");
	}

	// 给定一个正数K，返回所有出现次数>N/K的数，K=2就是水王问题
	public static void printKfMajor(int[] arr, int k) {
		if (k < 2) {
			System.out.println("the value of K is invalid.");
			return;
		}
		// 攒候选，cands候选表，最多k-1条记录，因为大于 n/k 次的数字，最多有k-1个
		Map<Integer, Integer> cands = new HashMap<>();
		for (int x : arr) {
			Integer hp = cands.get(x);
			if (hp != null) cands.put(x, hp + 1);
			else {
				// 当前数肯定不要！，每一个候选付出1点血量，血量变成0的候选，要删掉！
				if (cands.size() == k - 1) minusCandHp(cands);
				else cands.put(x, 1);
			}
		}
		// 所有可能的候选，都在cands表中！遍历一遍arr，每个候选收集真实次数
		Map<Integer, Integer> candCnt = getCandHp(arr, cands.keySet());
		boolean hasPrint = false;
		for (Integer key : cands.keySet()) {
			if (candCnt.get(key) > arr.length / k) {
				hasPrint = true;
				System.out.print(key + " ");
			}
		}
		System.out.println(hasPrint ? "" : "no such number.");
	}
	private static void minusCandHp(Map<Integer, Integer> map) {
		List<Integer> removeList = new LinkedList<>();
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			Integer key = entry.getKey(), value = entry.getValue();
			if (value == 1) {
				removeList.add(key);
			}
			map.put(key, value - 1);
		}
		for (Integer removeKey : removeList) map.remove(removeKey);
	}
	private static Map<Integer, Integer> getCandHp(int[] arr, Set<Integer> candSet) {
		Map<Integer, Integer> candCnt = new HashMap<>();
		for (int i = 0; i != arr.length; i++) {
			if (!candSet.contains(arr[i])) continue;
			candCnt.compute(arr[i], (k, v) -> v == null ? 1 : v + 1);
		}
		return candCnt;
	}

	public static void main(String[] args) {
		int[] arr = { 1, 2, 3, 1, 1, 2, 1 };
		printHalfMajor(arr);
		int K = 4;
		printKfMajor(arr, K);
	}

}
