package com.yw.course.coding.class28;

/**
 * @author yangwei
 */
public class Problem_0012_IntegerToRoman {

	// 方法一：
	public String intToRoman(int num) {
		String[][] map = {
				// 空, 1, 2, 3, 4, 5, 6, 7, 8, 9
				{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}, // 个位
				// 空, 10, 20, 30, 40, 50, 60, 70, 80, 90
				{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"}, // 十位
				// 空, 100, 200, 300, 400, 500, 600, 700, 800, 900
				{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}, // 百位
				// 空, 1000, 2000, 3000
				{"", "M", "MM", "MMM"} // 千位
		};
		StringBuilder sb = new StringBuilder()
				.append(map[3][num / 1000 % 10])
				.append(map[2][num / 100 % 10])
				.append(map[1][num / 10 % 10])
				.append(map[0][num % 10]);
		return sb.toString();
	}

	// 方法二：
	public String intToRoman2(int num) {
		int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
		String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
		StringBuilder ans = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			int value = values[i];
			String symbol = symbols[i];
			while (num >= value) {
				num -= value;
				ans.append(symbol);
			}
			if (num == 0) break;
		}
		return ans.toString();
	}

}
