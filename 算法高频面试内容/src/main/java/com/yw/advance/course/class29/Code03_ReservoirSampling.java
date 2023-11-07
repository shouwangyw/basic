package com.yw.advance.course.class29;

/**
 * @author yangwei
 */
public class Code03_ReservoirSampling {

	public static class RandomBox {
		private int[] bag;
		private int n;
		private int count;

		public RandomBox(int capacity) {
			bag = new int[capacity];
			n = capacity;
			count = 0;
		}
		public void add(int num) {
			count++;
			if (count <= n) bag[count - 1] = num;
			else if (rand(count) <= n) bag[rand(n) - 1] = num;
		}
		private int rand(int max) {
			return (int) (Math.random() * max) + 1;
		}
		public int[] choices() {
			int[] ans = new int[n];
			System.arraycopy(bag, 0, ans, 0, n);
			return ans;
		}
	}

	// 请等概率返回1~i中的一个数字
	public static int random(int i) {
		return (int) (Math.random() * i) + 1;
	}

	public static void main(String[] args) {
		System.out.println("hello");
		int test = 10000;
		int ballNum = 17;
		int[] count = new int[ballNum + 1];
		for (int i = 0; i < test; i++) {
			int[] bag = new int[10];
			int bagi = 0;
			for (int num = 1; num <= ballNum; num++) {
				if (num <= 10) {
					bag[bagi++] = num;
				} else { // num > 10
					if (random(num) <= 10) {
						// 一定要把num球入袋子
						bagi = (int) (Math.random() * 10);
						bag[bagi] = num;
					}
				}
			}
			for (int num : bag) {
				count[num]++;
			}
		}
		for (int i = 0; i <= ballNum; i++) {
			System.out.println(count[i]);
		}

		System.out.println("hello");
		int all = 100;
		int choose = 10;
		int testTimes = 50000;
		int[] counts = new int[all + 1];
		for (int i = 0; i < testTimes; i++) {
			RandomBox box = new RandomBox(choose);
			for (int num = 1; num <= all; num++) {
				box.add(num);
			}
			int[] ans = box.choices();
			for (int x : ans) {
				counts[x]++;
			}
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + " times : " + counts[i]);
		}

	}
}
