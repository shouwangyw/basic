package com.yw.basic.course.class05;

import java.util.HashSet;

/**
 * @author yangwei
 */
public class Code01_BitMap {
//  以下是错误的 1 是整型 32位的，1L 才是长整型 64 位
//	public static class BitMap {
//		private long[] bits;
//
//		// (max + 64) >> 6 => (max + 64) / 64
//		public BitMap(int max) {
//			bits = new long[(max + 64) >> 6];
//		}
//		// num >> 6 => num / 64 -> 定位到第几个数组位置
//		// num % 64 => num & 63 -> 定位到第几位
//		// 1 << (num & 63) => 1 向左移(num&63)这么多位，然后 或 运算进去
//		public void add(int num) {
//			bits[num >> 6] |= (1 << (num & 63));
//		}
//		// 同理，1 向左移(num&63)这么多位，然后 取反后(是0) 与 运算就可以删掉
//		public void delete(int num) {
//			bits[num >> 6] &= ~(1 << (num & 63));
//		}
//		// 同理，1 向左移(num&63)这么多位，然后 与 运算 为0则不存在，为1则存在
//		public boolean contains(int num) {
//			return (bits[num >> 6] & (1 << (num & 63))) != 0;
//		}
//	}

	public static class BitMap {
		private long[] bits;

		// (max + 64) >> 6 => (max + 64) / 64
		public BitMap(int max) {
			bits = new long[(max + 64) >> 6];
		}
		// num >> 6 => num / 64 -> 定位到第几个数组位置
		// num % 64 => num & 63 -> 定位到第几位
		// 1 << (num & 63) => 1 向左移(num&63)这么多位，然后 或 运算进去
		public void add(int num) {
			bits[num >> 6] |= (1L << (num & 63));
		}
		// 同理，1 向左移(num&63)这么多位，然后 取反后(是0) 与 运算就可以删掉
		public void delete(int num) {
			bits[num >> 6] &= ~(1L << (num & 63));
		}
		// 同理，1 向左移(num&63)这么多位，然后 与 运算 为0则不存在，为1则存在
		public boolean contains(int num) {
			return (bits[num >> 6] & (1L << (num & 63))) != 0;
		}
	}

	public static void main(String[] args) {
		System.out.println("测试开始！");
		int max = 10000;
		BitMap bitMap = new BitMap(max);
		HashSet<Integer> set = new HashSet<>();
		int testTime = 10000000;
		for (int i = 0; i < testTime; i++) {
			int num = (int) (Math.random() * (max + 1));
			double decide = Math.random();
			if (decide < 0.333) {
				bitMap.add(num);
				set.add(num);
			} else if (decide < 0.666) {
				bitMap.delete(num);
				set.remove(num);
			} else {
				if (bitMap.contains(num) != set.contains(num)) {
					System.out.println("Oops!");
					break;
				}
			}
		}
		for (int num = 0; num <= max; num++) {
			if (bitMap.contains(num) != set.contains(num)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束！");
	}

}
