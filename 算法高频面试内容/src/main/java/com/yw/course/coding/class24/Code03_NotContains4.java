package com.yw.course.coding.class24;

/**
 * 里程表不能含有4，给定一个数num，返回他是里程表里的第几个
 * @author yangwei
 */
public class Code03_NotContains4 {

	// 方法一：暴力解
	public static long notContains4Num0(long num) {
		long cnt = 0;
		for (int i = 1; i <= num; i++) {
			if (isNot4(i)) cnt++;
		}
		return cnt;
	}
	private static boolean isNot4(long num) {
		while (num != 0) {
			if (num % 10 == 4) return false;
			num /= 10;
		}
		return true;
	}

	// 方法二：时间复杂度O(lgN)
	public static long notContains4Num(long num) {
		if (num <= 0) return 0;
		// len: num的十进制位数
		int len = decimalLength(num);
		// 35621
		// 10000
		long offset = (long) Math.pow(10, len);
		long first = num / offset;
		return arr[len] * (first < 4 ? first : first - 1) - 1 + process(num % offset, offset / 10, len - 1);
	}
	// arr[1] : 比1位数还少1位，有几个数(数字里可以包含0但是不可以包含4)？0个
	// arr[2] : 比2位数还少1位，有几个数(数字里可以包含0但是不可以包含4)？9个
	// 1 -> 0 1 2 3 - 5 6 7 8 9 = 9
	// arr[3] :比3位数还少1位，有几个数(数字里可以包含0但是不可以包含4)？81个
	// 1 : 0 (0 1 2 3 - 5 6 7 8 9) = 9
	// 2 :
	// 1 (0 1 2 3 - 5 6 7 8 9) = 9
	// 2 (0 1 2 3 - 5 6 7 8 9) = 9
	// 3 (0 1 2 3 - 5 6 7 8 9) = 9
	// 5 (0 1 2 3 - 5 6 7 8 9) = 9
	// ...
	// 9 (0 1 2 3 - 5 6 7 8 9) = 9
	private static long[] arr = { 0L, 1L, 9L, 81L, 729L, 6561L, 59049L, 531441L, 4782969L, 43046721L, 387420489L,
			3486784401L, 31381059609L, 282429536481L, 2541865828329L, 22876792454961L, 205891132094649L,
			1853020188851841L, 16677181699666569L, 150094635296999121L, 1350851717672992089L };
	// num之前，有开头！
	// 在算0的情况下，num是第几个数字
	// num 76217 10000
	// 		6217 1000
	private static long process(long num, long offset, int len) {
		if (len == 0) return 1;
		long first = num / offset;
		return arr[len] * (first < 4 ? first : first - 1) + process(num % offset, offset / 10, len - 1);
	}
	// num的十进制位数
	// num = 7653210 返回 7
	private static int decimalLength(long num) {
		int len = 0;
		while (num != 0) {
			len++;
			num /= 10;
		}
		return len;
	}

	// 方法三：最优解
	// 讲完之后想到了课上同学的留言
	// 突然意识到，这道题的本质是一个9进制的数转成10进制的数
	// 不过好在课上的解法有实际意义，就是这种求解的方式，很多题目都这么弄
	// 还有课上的时间复杂度和"9进制的数转成10进制的数"的做法，时间复杂度都是O(lg N)
	// 不过"9进制的数转成10进制的数"毫无疑问是最优解
	public static long notContains4NumOptimal(long num) {
		if (num <= 0) return 0;
		long ans = 0;
		for (long base = 1; num != 0; num /= 10, base *= 9) {
			long cur = num % 10;
			ans += (cur < 4 ? cur : cur - 1) * base;
		}
		return ans;
	}

	public static void main(String[] args) {
		long max = 88888888L;
		System.out.println("功能测试开始，验证 0 ~ " + max + " 以内所有的结果");
		for (long i = 0; i <= max; i++) {
			// 测试的时候，输入的数字i里不能含有4，这是题目的规定！
//			if (isNot4(i) && notContains4Num0(i) != notContains4Num(i)) {
			if (isNot4(i) && notContains4Num(i) != notContains4NumOptimal(i)) {
				System.out.println("Oops!" + " " + i);
				break;
			}
		}
		System.out.println("如果没有打印Oops说明验证通过");

		long num = 8173528638135L;
//		long num = 817334L;
		long start;
		long end;
		System.out.println("性能测试开始，计算 num = " + num + " 的答案");

//		start = System.currentTimeMillis();
//		long ans1 = notContains4Num0(num);
//		end = System.currentTimeMillis();
//		System.out.println("方法一答案 : " + ans1 + ", 运行时间 : " + (end - start) + " ms");

		start = System.currentTimeMillis();
		long ans2 = notContains4Num(num);
		end = System.currentTimeMillis();
		System.out.println("方法二答案 : " + ans2 + ", 运行时间 : " + (end - start) + " ms");

		start = System.currentTimeMillis();
		long ans3 = notContains4NumOptimal(num);
		end = System.currentTimeMillis();
		System.out.println("方法三答案 : " + ans3 + ", 运行时间 : " + (end - start) + " ms");
	}

}
