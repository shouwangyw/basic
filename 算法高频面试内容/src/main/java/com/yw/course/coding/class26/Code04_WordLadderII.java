package com.yw.course.coding.class26;

import java.util.*;

/**
 * 测试链接: https://leetcode.cn/problems/word-ladder-ii/
 *
 * @author yangwei
 */
public class Code04_WordLadderII {

//    public static List<List<String>> findLadders(String start, String end, List<String> list) {
//        list.add(start);
//        HashMap<String, List<String>> nexts = getNexts(list);
//        HashMap<String, Integer> distances = getDistances(start, nexts);
//        LinkedList<String> pathList = new LinkedList<>();
//        List<List<String>> res = new ArrayList<>();
//        getShortestPaths(start, end, nexts, distances, pathList, res);
//        return res;
//    }
//
//    //
//    public static HashMap<String, List<String>> getNexts(List<String> words) {
//        HashSet<String> dict = new HashSet<>(words);
//        HashMap<String, List<String>> nexts = new HashMap<>();
//        for (String word : words) {
//            nexts.put(word, getNext(word, dict));
//        }
//        return nexts;
//    }
//
//    // word, 在表中，有哪些邻居，把邻居们，生成list返回
//    public static List<String> getNext(String word, HashSet<String> dict) {
//        ArrayList<String> res = new ArrayList<String>();
//        char[] chs = word.toCharArray();
//        for (char cur = 'a'; cur <= 'z'; cur++) {
//            for (int i = 0; i < chs.length; i++) {
//                if (chs[i] != cur) {
//                    char tmp = chs[i];
//                    chs[i] = cur;
//                    if (dict.contains(String.valueOf(chs))) {
//                        res.add(String.valueOf(chs));
//                    }
//                    chs[i] = tmp;
//                }
//            }
//        }
//        return res;
//    }
//
//    // 生成距离表，从start开始，根据邻居表，宽度优先遍历，对于能够遇到的所有字符串，生成(字符串，距离)这条记录，放入距离表中
//    public static HashMap<String, Integer> getDistances(String start, HashMap<String, List<String>> nexts) {
//        HashMap<String, Integer> distances = new HashMap<>();
//        distances.put(start, 0);
//        Queue<String> queue = new LinkedList<>();
//        queue.add(start);
//        HashSet<String> set = new HashSet<>();
//        set.add(start);
//        while (!queue.isEmpty()) {
//            String cur = queue.poll();
//            for (String next : nexts.get(cur)) {
//                if (!set.contains(next)) {
//                    distances.put(next, distances.get(cur) + 1);
//                    queue.add(next);
//                    set.add(next);
//                }
//            }
//        }
//        return distances;
//    }
//
//    // cur 当前来到的字符串 可变
//    // to 目标，固定参数
//    // nexts 每一个字符串的邻居表
//    // cur 到开头距离5 -> 到开头距离是6的支路 distances距离表
//    // path : 来到cur之前，深度优先遍历之前的历史是什么
//    // res : 当遇到cur，把历史，放入res，作为一个结果
//    public static void getShortestPaths(String cur, String to, HashMap<String, List<String>> nexts,
//                                        HashMap<String, Integer> distances, LinkedList<String> path, List<List<String>> res) {
//        path.add(cur);
//        if (to.equals(cur)) {
//            res.add(new LinkedList<>(path));
//        } else {
//            for (String next : nexts.get(cur)) {
//                if (distances.get(next) == distances.get(cur) + 1) {
//                    getShortestPaths(next, to, nexts, distances, path, res);
//                }
//            }
//        }
//        path.pollLast();
//    }


    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        wordList.add(beginWord);
        // 预处理，生成邻居表和距离表
        Map<String, List<String>> nexts = getNexts(wordList);
        Map<String, Integer> distances = getDistances(beginWord, nexts);
        List<List<String>> ans = new LinkedList<>();
        LinkedList<String> path = new LinkedList<>();
        getShortestPaths(beginWord, endWord, nexts, distances, path, ans);
        return ans;
    }
    // 生成所有单词的邻居单词
    private static Map<String, List<String>> getNexts(List<String> words) {
        Set<String> dict = new HashSet<>(words);
        Map<String, List<String>> nexts = new HashMap<>();
        for (String word : words) nexts.put(word, getNext(word, dict));
        return nexts;
    }
    private static List<String> getNext(String word, Set<String> dict) {
        List<String> next = new LinkedList<>();
        char[] cs = word.toCharArray();
        for (char c = 'a'; c <= 'z'; c++) {
            for (int i = 0; i < cs.length; i++) {
                if (cs[i] == c) continue;
                char tmp = cs[i];
                cs[i] = c;
                String s = String.valueOf(cs);
                if (dict.contains(s)) next.add(s);
                cs[i] = tmp;
            }
        }
        return next;
    }
    // 生成距离表，从start开始，根据邻居表，宽度优先遍历，对于能够遇到的所有字符串，生成(字符串，距离)这条记录，放入距离表中
    private static Map<String, Integer> getDistances(String start, Map<String, List<String>> nexts) {
        Map<String, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        Queue<String> q = new LinkedList<>();
        q.add(start);
        Set<String> visited = new HashSet<>();
        visited.add(start);
        while (!q.isEmpty()) {
            String cur = q.poll();
            for (String next : nexts.get(cur)) {
                if (visited.contains(next)) continue;
                distances.put(next, distances.get(cur) + 1);
                q.add(next);
                visited.add(next);
            }
        }
        return distances;
    }
    // 当前来到字符串cur，目标 target，path: 来到cur之前，深度优先遍历之前的历史是什么
    private static void getShortestPaths(String cur, String target, Map<String, List<String>> nexts, Map<String, Integer> distances, LinkedList<String> path, List<List<String>> ans) {
        path.add(cur);
        if (target.equals(cur)) ans.add(new LinkedList<>(path));
        else {
            for (String next : nexts.get(cur)) {
                if (distances.get(next) == distances.get(cur) + 1)
                    getShortestPaths(next, target, nexts, distances, path, ans);
            }
        }
        path.pollLast();
    }
}
