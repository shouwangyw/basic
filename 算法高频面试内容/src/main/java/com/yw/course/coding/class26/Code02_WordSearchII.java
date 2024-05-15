package com.yw.course.coding.class26;

import java.util.*;

/**
 * 测试链接 : https://leetcode.cn/problems/word-search-ii/
 *
 * @author yangwei
 */
public class Code02_WordSearchII {

    public List<String> findWords(char[][] board, String[] words) {
        Trie trie = new Trie();
        Set<String> set = new HashSet<>();
        for (String word : words) {
            if (set.add(word)) trie.insert(word);
        }
        List<String> ans = new ArrayList<>();
        LinkedList<Character> path = new LinkedList<>();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                // 枚举board中的每一个位置，并收集答案
                process(board, r, c, path, trie.root, ans);
            }
        }
        return ans;
    }
    private static int[][] dirs = { {1, 0}, {0, 1}, {-1, 0}, {0, -1} };
    // 从board[r][c]位置的字符出发，之前走过的路径记录在path中，node是当前待检查的节点，返回找到答案的数量
    private static int process(char[][] board, int r, int c, LinkedList<Character> path, Node node, List<String> ans) {
        if (r < 0 || r == board.length || c < 0 || c == board[0].length) return 0;
        // 用 0 标识之前走过的位置
        char cur = board[r][c];
        if (cur == 0) return 0;
        int idx = cur - 'a';
        // 如果没路 或 这条路上最终字符之前加入过结果里
        if (node.nexts[idx] == null || node.nexts[idx].p == 0) return 0;
        node = node.nexts[idx];
        // 当前位置的字符加到路径里去
        path.addLast(cur);
        int fix = 0; // 记录一共搞定了多少答案
        if (node.e > 0) {
            ans.add(genPath(path));
            node.e--;
            fix++;
        }
        // 当前位置标识为0，避免走回头路
        board[r][c] = 0;
        // 往上、下、左、右，四个方向尝试
        for (int[] dir : dirs) {
            fix += process(board, r + dir[0], c + dir[1], path, node, ans);
        }
        // DFS回溯
        board[r][c] = cur;
        path.pollLast();
        node.p -= fix;
        return fix;
    }
    private static String genPath(List<Character> path) {
        StringBuilder sb = new StringBuilder();
        path.forEach(c -> sb.append(c));
        return sb.toString();
    }
    // 定义前缀树节点
    private static class Node {
        private Node[] nexts;
        private int p; // 进过的次数
        private int e; // 是否是单词结尾
        public Node() {
            this.nexts = new Node[26];
            this.p = 0;
            this.e = 0;
        }
    }
    // 前缀树
    private static class Trie {
        private final Node root;
        public Trie() {
            this.root = new Node();
        }
        // 添加单词
        public void insert(String word) {
            if (word == null || word.length() == 0) return;
            Node node = root;
            node.p++;
            for (char c : word.toCharArray()) {
                int path = c - 'a';
                // 如果path这条路不存在，则创建之
                if (node.nexts[path] == null) node.nexts[path] = new Node();
                // 否则node往下走
                node = node.nexts[path];
                node.p++;
            }
            node.e++;
        }
    }

}
