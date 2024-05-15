package com.yw.course.coding.class24;

/**
 * 测试链接 : https://leetcode.cn/problems/minimum-window-substring/
 *
 * @author yangwei
 */
public class Code05_MinWindowLength {

    // 滑动窗口
    public String minWindow(String s, String t) {
        char[] cs = s.toCharArray(), ct = t.toCharArray();
        int m = cs.length, n = ct.length;
        int[] map = new int[256];
        for (char c : ct) map[c]++;
        // 滑动窗口[l,r]
        int l = 0, r = 0, minLen = Integer.MAX_VALUE, cnt = n, ansL = -1, ansR = -1;
        while (r < m) {
            if (--map[cs[r]] >= 0) cnt--;
            if (cnt == 0) { // 凑齐了
                while (map[cs[l]] < 0) map[cs[l++]]++;
                if (minLen > r - l + 1) {
                    minLen = r - l + 1;
                    ansL = l;
                    ansR = r;
                }
                cnt++;
                map[cs[l++]]++;
            }
            r++;
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(ansL, ansR + 1);
    }
}
