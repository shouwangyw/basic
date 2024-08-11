package com.yw.course.coding.class32;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class Problem_0166_FractionToRecurringDecimal {

	public static String fractionToDecimal(int numerator, int denominator) {
		if (numerator == 0) return "0";
		StringBuilder ans = new StringBuilder();
		ans.append(((numerator > 0) ^ (denominator > 0)) ? "-" : "");
		long num = Math.abs((long) numerator), den = Math.abs((long) denominator);
		ans.append(num / den);
		num %= den;
		if (num == 0) return ans.toString();
		ans.append(".");
		Map<Long, Integer> map = new HashMap<>();
		map.put(num, ans.length());
		while (num != 0) {
			num *= 10;
			ans.append(num / den);
			num %= den;
			Integer idx = map.get(num);
			if (idx == null) map.put(num, ans.length());
			else { ans.insert(idx, "(").append(")"); break; }
		}
		return ans.toString();
}

	public static void main(String[] args) {
		System.out.println(fractionToDecimal(127, 999));
	}

}
