package com.yw.course.coding.class09;

import java.util.Arrays;

/**
 * 给定一个数组arr，长度为N，arr中的值不是0就是1
 * arr[i]表示第i栈灯的状态，0代表灭灯，1代表亮灯
 * 每一栈灯都有开关，但是按下i号灯的开关，会同时改变i-1、i、i+2栈灯的状态
 * 问题一：
 * 如果N栈灯排成一条直线,请问最少按下多少次开关,能让灯都亮起来
 * 排成一条直线说明：
 * i为中间位置时，i号灯的开关能影响i-1、i和i+1
 * 0号灯的开关只能影响0和1位置的灯
 * N-1号灯的开关只能影响N-2和N-1位置的灯
 * 
 * 问题二：
 * 如果N栈灯排成一个圈,请问最少按下多少次开关,能让灯都亮起来
 * 排成一个圈说明：
 * i为中间位置时，i号灯的开关能影响i-1、i和i+1
 * 0号灯的开关能影响N-1、0和1位置的灯
 * N-1号灯的开关能影响N-2、N-1和0位置的灯
 *
 * @author yangwei
 */
public class Code01_LightProblem {

	// 无环按灯问题
	// 方法一：暴力尝试递归版本
	public static int minWaysNoLoop(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		if (arr.length == 1) return arr[0] ^ 1;
		if (arr.length == 2) return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		// 不变0位置的状态
		int ways1 = process(arr, 2, arr[0], arr[1]);
		// 变0位置的状态
		int ways2 = process(arr, 2, arr[0] ^ 1, arr[1] ^ 1);
		if (ways2 != Integer.MAX_VALUE) ways2++;
		return Math.min(ways1, ways2);
	}
	// 0~idx-2这一段区间灯的状态都是1，当前位置是idx-1，状态curState，来到 idx 位置做决定，当前位置之前idx-2位置状态preState
	// 返回可以让后续所有灯都亮起来的最少按开关次数
	private static int process(int[] arr, int idx, int preState, int curState) {
		// base case: 到了最后一个开关
		if (idx == arr.length) return curState != preState ? Integer.MAX_VALUE : (curState ^ 1);
		// 如果之前状态为0，则当前位置一定是按了开关
		if (preState == 0) {
			int ways = process(arr, idx + 1, curState ^ 1, arr[idx] ^ 1);
			return ways == Integer.MAX_VALUE ? ways : ways + 1;
		}
		// 否则当前位置一定是没按开关
		return process(arr, idx + 1, curState, arr[idx]);
	}

	// 方法二：暴力尝试迭代版本
	public static int minWaysNoLoop2(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		if (arr.length == 1) return arr[0] ^ 1;
		if (arr.length == 2) return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		// 不变0位置的状态
		int ways1 = process(arr, arr[0], arr[1]);
		// 变0位置的状态
		int ways2 = process(arr, arr[0] ^ 1, arr[1] ^ 1);
		if (ways2 != Integer.MAX_VALUE) ways2++;
		return Math.min(ways1, ways2);
	}
	private static int process(int[] arr, int preState, int curState) {
		int i = 2;
		int op = 0;
		while (i != arr.length) {
			if (preState == 0) {
				op++;
				preState = curState ^ 1;
				curState = arr[i++] ^ 1;
			} else {
				preState = curState;
				curState = arr[i++];
			}
		}
		return (preState != curState) ? Integer.MAX_VALUE : (op + (curState ^ 1));
	}

	// 有环按灯问题
	public static int minWaysHasLoop(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		int n = arr.length;
		if (n == 1) return arr[0] ^ 1;
		if (n == 2) return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		if (n == 3) return (arr[0] != arr[1] || arr[0] != arr[2]) ? Integer.MAX_VALUE : (arr[0] ^ 1);
		// 0位置不变，1位置不变
		int ways1 = process(arr, 3, arr[1], arr[2], arr[0], arr[n - 1]);
		// 0位置变，1位置不变
		int ways2 = process(arr, 3, arr[1] ^ 1, arr[2], arr[0] ^ 1, arr[n - 1] ^ 1);
		// 0位置不变，1位置变
		int ways3 = process(arr, 3, arr[1] ^ 1, arr[2] ^ 1, arr[0] ^ 1, arr[n - 1]);
		// 0位置变，1位置变
		int ways4 = process(arr, 3, arr[1], arr[2] ^ 1, arr[0], arr[n - 1] ^ 1);
		ways2 = ways2 != Integer.MAX_VALUE ? (ways2 + 1) : ways2;
		ways3 = ways3 != Integer.MAX_VALUE ? (ways3 + 1) : ways3;
		ways4 = ways4 != Integer.MAX_VALUE ? (ways4 + 2) : ways4;
		return Math.min(Math.min(ways1, ways2), Math.min(ways3, ways4));
	}
	private static int process(int[] arr, int idx, int preState, int curState, int firstState, int endState) {
		if (idx == arr.length) return (firstState != endState || preState != endState) ? Integer.MAX_VALUE : (endState ^ 1);
		boolean endFlag = idx == arr.length - 1;
		if(preState == 0) {
			int next = process(arr, idx + 1, curState ^ 1, endFlag ? (endState ^ 1) : arr[idx] ^ 1, firstState, endFlag ? endState ^ 1 : endState);
			return next == Integer.MAX_VALUE ? next : (next + 1);
		}
		return process(arr, idx + 1, curState, endFlag ? endState : arr[idx], firstState, endState);
	}
	// 有环改灯问题的迭代版本
	public static int minWaysHasLoop2(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		int n = arr.length;
		if (n == 1) return arr[0] ^ 1;
		if (n == 2) return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		if (n == 3) return (arr[0] != arr[1] || arr[0] != arr[2]) ? Integer.MAX_VALUE : (arr[0] ^ 1);
		// 0位置不变，1位置不变
		int ways1 = process(arr, arr[1], arr[2], arr[0], arr[n - 1]);
		// 0位置变，1位置不变
		int ways2 = process(arr, arr[1] ^ 1, arr[2], arr[0] ^ 1, arr[n - 1] ^ 1);
		// 0位置不变，1位置变
		int ways3 = process(arr, arr[1] ^ 1, arr[2] ^ 1, arr[0] ^ 1, arr[n - 1]);
		// 0位置变，1位置变
		int ways4 = process(arr, arr[1], arr[2] ^ 1, arr[0], arr[n - 1] ^ 1);
		ways2 = ways2 != Integer.MAX_VALUE ? (ways2 + 1) : ways2;
		ways3 = ways3 != Integer.MAX_VALUE ? (ways3 + 1) : ways3;
		ways4 = ways4 != Integer.MAX_VALUE ? (ways4 + 2) : ways4;
		return Math.min(Math.min(ways1, ways2), Math.min(ways3, ways4));
	}

	public static int process(int[] arr, int preState, int curState, int firstState, int endState) {
		int i = 3;
		int op = 0;
		while (i < arr.length - 1) {
			if (preState == 0) {
				op++;
				preState = curState ^ 1;
				curState = (arr[i++] ^ 1);
			} else {
				preState = curState;
				curState = arr[i++];
			}
		}
		if (preState == 0) {
			op++;
			preState = curState ^ 1;
			endState ^= 1;
		} else {
			preState = curState;
		}
		return (endState != firstState || endState != preState) ? Integer.MAX_VALUE : (op + (endState ^ 1));
	}


	public static void main(String[] args) {
		System.out.println("如果没有任何Oops打印，说明所有方法都正确");
		System.out.println("test begin");
		int testTime = 20000;
		int lenMax = 12;
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * lenMax);
			int[] arr = randomArray(len);
			int ans1 = minWaysNoLoop(arr);
			int ans2 = minWaysNoLoop2(arr);
			if (ans1 != ans2) {
				System.out.println(Arrays.toString(arr));
				System.out.println("Oops!, ans1 = " + ans1 + ", ans2 = " + ans2);
				break;
			}
		}
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * lenMax);
			int[] arr = randomArray(len);
			int ans1 = minWaysHasLoop(arr);
			int ans2 = minWaysHasLoop2(arr);
			if (ans1 != ans2) {
				System.out.println(Arrays.toString(arr));
				System.out.println("Oops!, ans1 = " + ans1 + ", ans2 = " + ans2);
				break;
			}
		}
		System.out.println("test end");

		int len = 100000000;
		System.out.println("性能测试");
		System.out.println("数组大小：" + len);
		int[] arr = randomArray(len);
		long start = 0;
		long end = 0;
		start = System.currentTimeMillis();
		minWaysNoLoop2(arr);
		end = System.currentTimeMillis();
		System.out.println("noLoopMinStep2 run time: " + (end - start) + "(ms)");

		start = System.currentTimeMillis();
		minWaysHasLoop2(arr);
		end = System.currentTimeMillis();
		System.out.println("loopMinStep2 run time: " + (end - start) + "(ms)");
	}

	// 生成长度为len的随机数组，值只有0和1两种值
	private static int[] randomArray(int len) {
		int[] arr = new int[len];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 2);
		}
		return arr;
	}

}
