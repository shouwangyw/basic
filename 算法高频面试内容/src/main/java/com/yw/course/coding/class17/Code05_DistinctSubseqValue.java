package com.yw.course.coding.class17;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试链接: https://leetcode.cn/problems/distinct-subsequences-ii/
 * @author yangwei
 */
public class Code05_DistinctSubseqValue {

    public static int distinctSubseqII(String s) {
        char[] cs = s.toCharArray();
        int[] cnt = new int[26];
        int mod = 1000000007, ans = 1; // 空串也算
        for (char c : cs) {
            int add = (ans - cnt[c - 'a'] + mod) % mod;
            ans = (ans + add) % mod;
            ans = ans == 0 ? mod : ans;
            cnt[c - 'a'] = (cnt[c - 'a'] + add) % mod;
        }
        return ans - 1;
    }

    public static int distinctSubseqII2(String s) {
        // map(k,v): 记录字符k到来时，新加入子序列数量
        Map<Character, Integer> map = new HashMap<>();
        // ans = 1: 还没有遍历字符时，空串也算作一个序列，最后答案减掉一个
        int ans = 1, mod = 1000000007;
        for (char c : s.toCharArray()) {
            int cur = ((ans * 2) % mod - map.getOrDefault(c, 0) + mod) % mod;
            map.put(c, ans);
            // 新增测试用例会导致cur==0
            ans = cur == 0 ? mod : cur;
        }
        return ans - 1;
    }

	public static void main(String[] args) {
		String s = "bccaccbaabbc";
		System.out.println(distinctSubseqII(s));
		System.out.println(distinctSubseqII2(s));
	}

}
