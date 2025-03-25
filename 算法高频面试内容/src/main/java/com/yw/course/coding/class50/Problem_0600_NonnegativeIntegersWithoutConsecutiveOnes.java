package com.yw.course.coding.class50;

/**
 * @author yangwei
 */
public class Problem_0600_NonnegativeIntegersWithoutConsecutiveOnes {

//	// f(0, false, 5,n)
//	// 6 5 ... 0 -1 n
//	// 0 ? 停
//
//	//     5 4 3 2 1 0 -1
//	// n : 1 0 1 1 1 0
//	// 0 1 i
//	// pre : 第i+1位做的决定，0还是1
//	// 在 第i+1位做的决定 是pre的情况下，从index位开始，往后都做决定！
//	// 但是，不能有相邻的1，请问有多少决定！返回！
//	// alreadyLess : 之前的决定，是不是已经导致你到index的时候，已经比n小了！
//	// pre -> 0 1
//	// alreadyLess -> true false
//	// index -> n的位数，（logN）
//	// 2 * 2 * logN
//	// dp[2][]
//	// int alreadyLess  0  1
//	public int f(int pre, boolean alreadyLess, int index, int n) {
//		if (index == -1) {
//			return 1;
//		}
//		if (pre == 1) {
//			// 只能做0的决定，然后去往i-1位置
//			boolean curLessOrMore = alreadyLess | ((n & 1 << index) != 0);
//			return f(0, curLessOrMore, index - 1, n);
//		} else { // pre == 0的决定
//			// 可能性1，继续做0的决定
//			boolean curLessOrMore = alreadyLess | ((n & 1 << index) != 0);
//			int p1 = f(0, curLessOrMore, index - 1, n);
//			// 可能性2，做1的决定
//			// 1)pre == 0的决定, 2)
//			int p2 = 0;
//			if (alreadyLess || (n & 1 << index) != 0) {
//				p2 = f(1, alreadyLess, index - 1, n);
//			}
//			return p1 + p2;
//		}
//	}

    public int findIntegers(int n) {
        int i = 31;
        // 找到n的最高位1的位置
        for (; i >= 0; i--) {
            if ((n & (1 << i)) != 0) break;
        }
        // 初始化缓存，处理从最高位到最低位
        int[][][] cache = new int[2][2][i + 1];
        return process(0, 0, i, n, cache);
    }

    private static int process(int pre, int isLess, int idx, int num, int[][][] cache) {
        if (idx == -1) return 1; // 终止条件：处理完所有位
        if (cache[pre][isLess][idx] != 0) return cache[pre][isLess][idx]; // 查缓存

        int res = 0;
        if (pre == 1) { // 前一位是1，当前必须填0
            // 更新isLess：若num的当前位为1且填0，则后续可自由选择
            int newIsLess = Math.max(isLess, (num & (1 << idx)) != 0 ? 1 : 0);
            res = process(0, newIsLess, idx - 1, num, cache);
        } else { // 前一位是0，当前可填0或1
            if ((num & (1 << idx)) == 0 && isLess == 0) { // 必须填0
                res = process(0, isLess, idx - 1, num, cache);
            } else { // 可填0或1
                // 填1：后续pre=1，isLess保持当前状态（若num当前位为1则需匹配）
                res += process(1, isLess, idx - 1, num, cache);
                // 填0：更新isLess（若num当前位为1，填0后后续可自由选择）
                int newIsLess = Math.max(isLess, (num & (1 << idx)) != 0 ? 1 : 0);
                res += process(0, newIsLess, idx - 1, num, cache);
            }
        }
        cache[pre][isLess][idx] = res; // 存缓存
        return res;
    }

}
