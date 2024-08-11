package com.yw.course.coding.class32;

/**
 * @author yangwei
 */
public class Problem_0171_ExcelSheetColumnNumber {

	// 这道题反过来也要会写
	public int titleToNumber(String columnTitle) {
		int ans = 0;
		for (int i = 0; i < columnTitle.length(); i++)
			ans = ans * 26 + (columnTitle.charAt(i) - 'A') + 1;
		return ans;
	}

}
