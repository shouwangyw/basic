package com.yw.course.coding.class51;

/**
 * @author yangwei
 */
public class Problem_0875_KokoEatingBananas {

	public int minEatingSpeed(int[] piles, int h) {
		long l = 1, r = 0;
		for (int pile : piles) r = Math.max(r, pile);
		long ans = 0, mid;
		while (l <= r) {
			mid = l + ((r - l) >> 1);
			if (cost(piles, mid) <= h) {
				ans = mid;
				r = mid - 1;
			} else l = mid + 1;
		}
		return (int) ans;
	}
	// 以speed速度吃香蕉，需要多长时间
	private static long cost(int[] piles, long speed) {
		long time = 0, offset = speed - 1;
		for (int pile : piles) time += (pile + offset) / speed;
		return time;
	}

}
