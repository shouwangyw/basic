package com.yw.course.coding.class13;

import java.math.BigDecimal;

/**
 * @author yangwei
 */
public class Code01_NCardsABWin {

	// 谷歌面试题
	// 面值为1~10的牌组成一组，
	// 每次你从组里等概率的抽出1~10中的一张
	// 下次抽会换一个新的组，有无限组
	// 当累加和<17时，你将一直抽牌
	// 当累加和>=17且<21时，你将获胜
	// 当累加和>=21时，你将失败
	// 返回获胜的概率
	public static double f1() {
		return p1(0);
	}
	// 返回当来到cur这个累加和时，获胜的概率
	private static double p1(int cur) {
		if (cur >= 17 && cur < 21) return 1.0;
		if (cur >= 21) return 0.0;
		double w = 0.0;
		for (int i = 1; i <= 10; i++) w += p1(cur + i);
		return w / 10;
	}

	// 谷歌面试题扩展版
	// 面值为1~N的牌组成一组，
	// 每次你从组里等概率的抽出1~N中的一张
	// 下次抽会换一个新的组，有无限组
	// 当累加和<a时，你将一直抽牌
	// 当累加和>=a且<b时，你将获胜
	// 当累加和>=b时，你将失败
	// 返回获胜的概率，给定的参数为N，a，b
	public static double f2(int n, int a, int b) {
		if (n < 1 || a >= b || a < 0) return 0.0;
		if (b - a >= n) return 1.0;
		// 所有参数合法，并且 b-a < n
		return p2(0, n, a, b);
	}
	// 返回当来到cur这个累加和时，获胜的概率，n、a、b 是固定参数
	private static double p2(int cur, int n, int a, int b) {
		if (cur >= a && cur < b) return 1.0;
		if (cur >= b) return 0.0;
		double w = 0.0;
		for (int i = 1; i <= n; i++) w += p2(cur + i, n, a, b);
		return w / n;
	}

	// f2的改进版本，用到了观察位置优化枚举的技巧
	public static double f3(int n, int a, int b) {
		if (n < 1 || a >= b || a < 0) return 0.0;
		if (b - a >= n) return 1.0;
		return p3(0, n, a, b);
	}
	private static double p3(int cur, int n, int a, int b) {
		if (cur >= a && cur < b) return 1.0;
		if (cur >= b) return 0.0;
		if (cur == a - 1) return 1.0 * (b - a) / n;
		double w = p3(cur + 1, n, a, b) + p3(cur + 1, n, a, b) * n;
		// !!! 进行以下合并计算是有问题的
//		double w = p3(cur + 1, n, a, b) * (1 + n);
		if (cur + 1 + n < b) w -= p3(cur + 1 + n, n, a, b);
		return w / n;
	}

	// f3的改进版本的动态规划
	public static double f4(int n, int a, int b) {
		if (n < 1 || a >= b || a < 0) return 0.0;
		if (b - a >= n) return 1.0;
		double[] dp = new double[b];
		for (int i = a; i < b; i++) dp[i] = 1.0;
		if (a - 1 >= 0) dp[a - 1] = 1.0 * (b - a) / n;
		for (int cur = a - 2; cur >= 0; cur--) {
			double w = dp[cur + 1] + dp[cur + 1] * n;
			if (cur + 1 + n < b) w -= dp[cur + 1 + n];
			dp[cur] = w / n;
		}
		return dp[0];
	}

	public static void main(String[] args) {
		int N = 10;
		int a = 17;
		int b = 21;
		System.out.println("N = " + N + ", a = " + a + ", b = " + b);
		System.out.println(f1());
		System.out.println(f2(N, a, b));
		System.out.println(f3(N, a, b));
		System.out.println(f4(N, a, b));

		int maxN = 15;
		int maxM = 20;
		int testTime = 100000;
		System.out.println("测试开始");
		System.out.print("比对double类型答案可能会有精度对不准的问题, ");
		System.out.print("所以答案一律只保留小数点后四位进行比对, ");
		System.out.println("如果没有错误提示, 说明验证通过");
		for (int i = 0; i < testTime; i++) {
			N = (int) (Math.random() * maxN);
			a = (int) (Math.random() * maxM);
			b = (int) (Math.random() * maxM);
			double ans2 = new BigDecimal(f2(N, a, b)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			double ans3 = new BigDecimal(f3(N, a, b)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			double ans4 = new BigDecimal(f4(N, a, b)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (ans2 != ans3 || ans2 != ans4) {
				System.out.println("Oops!");
				System.out.println(N + " , " + a + " , " + b);
				System.out.println(ans2);
				System.out.println(ans3);
				System.out.println(ans4);
			}
		}
		System.out.println("测试结束");

		N = 10000;
		a = 67834;
		b = 72315;
		System.out.println("N = " + N + ", a = " + a + ", b = " + b + "时, 除了方法4外都超时");
		System.out.print("方法4答案: ");
		System.out.println(f4(N, a, b));
	}

}
