package com.yw.course.coding.class17;

import java.util.*;

/**
 * 测试链接 : https://leetcode.cn/problems/palindrome-pairs/
 * @author yangwei
 */
public class Code03_PalindromePairs {

    public List<List<Integer>> palindromePairs(String[] words) {
        // 创建字符串索引表
        Map<String, Integer> wordMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) wordMap.put(words[i], i);
        // 遍历字符串数组，并收集答案
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < words.length; i++) ans.addAll(findPalindromePairs(words[i], i, wordMap));
        return ans;
    }
    // word: 当前字符串，idx: 当前字符串所在位置
    private List<List<Integer>> findPalindromePairs(String word, int idx, Map<String, Integer> wordMap) {
        List<List<Integer>> ans = new ArrayList<>();
        String reverseWord = reverseString(word.toCharArray());
        Integer rest = wordMap.get("");
        // 如果有空串，且不是当前字符串，并且当前字符串是回文
        if (rest != null && rest != idx && word.equals(reverseWord)) {
            addAns(ans, rest, idx);
            addAns(ans, idx, rest);
        }
        // 以下通过Manacher算法判断回文
        // 得到当前字符串的回文半径数组
        int[] rs = manacher(word);
        int mid = rs.length >> 1;
        for (int i = 1; i < mid; i++) {
            if (i - rs[i] != -1) continue;
            rest = wordMap.get(reverseWord.substring(0, mid - i));
            if (rest != null && rest != idx) addAns(ans, rest, idx);
        }
        for (int i = mid + 1; i < rs.length; i++) {
            if (i + rs[i] != rs.length) continue;
            rest = wordMap.get(reverseWord.substring((mid << 1) - i));
            if (rest != null && rest != idx) addAns(ans, idx, rest);
        }
        return ans;
    }
    // 翻转字符串
    private String reverseString(char[] cs) {
        char[] res = new char[cs.length];
        for (int i = 0; i < cs.length; i++) res[i] = cs[cs.length - 1 - i];
        return new String(res);
    }
    private void addAns(List<List<Integer>> ans, int l, int r) {
        ans.addAll(Collections.singleton(Arrays.asList(l, r)));
    }
    private int[] manacher(String word) {
        // 得到Manacher串
        char[] ms = manacherString(word.toCharArray());
        int[] rs = new int[ms.length];  // 回文半径数组
        int c = -1, r = -1, n = rs.length;             // c: 中心点位置，r: 中心点c所能扩成功的最右边界+1，初始化都设置为-1
        for (int i = 0; i < n; i++) {
            rs[i] = r > i ? Math.min(rs[2 * c - i], r - i) : 1;
            while (i + rs[i] < n && i - rs[i] > -1) {
                if (ms[i + rs[i]] == ms[i - rs[i]]) rs[i]++;
                else break;
            }
            // 若i位置能将r推的更右，则更新r、c
            if (i + rs[i] > r) {
                r = i + rs[i];
                c = i;
            }
        }
        return rs;
    }
    private char[] manacherString(char[] cs) {
        char[] res = new char[cs.length * 2 + 1];
        for (int i = 0, j = 0; i < res.length; i++) res[i] = (i & 1) == 0 ? '#' : cs[j++];
        return res;
    }
}