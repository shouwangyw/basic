package com.yw.course.advance.class13;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static com.yw.util.CommonUtils.copyStringArray;
import static com.yw.util.CommonUtils.generateRandomStringArray;

/**
 * @author yangwei
 */
public class Code05_LowestLexicography {

    // 方法一：暴力
    public static String lowestStringTest(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        TreeSet<String> ans = (TreeSet<String>) process(strs);
        return ans.size() == 0 ? "" : ans.first();
    }
    // strs中所有字符串全排列，返回所有可能的结果
    private static Set<String> process(String[] strs) {
        Set<String> ans = new TreeSet<>();
        if (strs.length == 0) {
            ans.add("");
            return ans;
        }
        for (int i = 0; i < strs.length; i++) {
            String first = strs[i];
            String[] nexts = removeIndexString(strs, i);
            Set<String> next = process(nexts);
            for (String cur : next) {
                ans.add(first + cur);
            }
        }
        return ans;
    }
    // {"abc", "cks", "bct"}
    // 0 1 2
    // removeIndexString(arr , 1) -> {"abc", "bct"}
    private static String[] removeIndexString(String[] arr, int index) {
        int n = arr.length;
        String[] ans = new String[n - 1];
        int ansIndex = 0;
        for (int i = 0; i < n; i++) {
            if (i != index) {
                ans[ansIndex++] = arr[i];
            }
        }
        return ans;
    }

    // 方法二：贪心
    public static String lowestString(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        Arrays.sort(strs, (o1, o2) -> (o1 + o2).compareTo(o2 + o1));
        StringBuilder res = new StringBuilder();
        for (String str : strs) {
            res.append(str);
        }
        return res.toString();
    }

    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 10000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            if (!lowestStringTest(arr1).equals(lowestString(arr2))) {
                for (String str : arr1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
