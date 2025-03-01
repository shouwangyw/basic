package com.yw.course.coding.class38;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class Problem_0763_PartitionLabels {

	public List<Integer> partitionLabels(String s) {
		char[] cs = s.toCharArray();
		// 维护每个字符出现的最右位置
		int[] pos = new int[26];
		for (int i = 0; i < cs.length; i++) pos[cs[i] - 'a'] = i;
		List<Integer> ans = new ArrayList<>();
		int l = 0, r = pos[cs[0] - 'a'];
		for (int i = 1; i < cs.length; i++) {
			if (i > r) {
				ans.add(r - l + 1);
				l = i;
			}
			r = Math.max(r, pos[cs[i] - 'a']);
		}
		ans.add(r - l + 1);
		return ans;
	}

}
