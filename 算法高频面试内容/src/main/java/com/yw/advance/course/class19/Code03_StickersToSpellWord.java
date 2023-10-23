package com.yw.advance.course.class19;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试链接：https://leetcode.cn/problems/stickers-to-spell-word
 *
 * @author yangwei
 */
public class Code03_StickersToSpellWord {

    // 方法一：尝试暴力递归
    public int minStickers(String[] stickers, String target) {
        int ans = process(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
    // 所有贴纸stickers，每一种贴纸都有无穷张，目标target
    // 返回最少张数
    private static int process(String[] stickers, String target) {
        if (target.length() == 0) return 0;
        int min = Integer.MAX_VALUE;
        for (String first : stickers) {
            String rest = minus(target, first);
            if (rest.length() != target.length()) {
                min = Math.min(min, process(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }
    // 返回 s1 减去 s2 中字符后剩余的字符
    private static String minus(String s1, String s2) {
        int[] cnt = new int[26];
        for (char c : s1.toCharArray()) cnt[c - 'a']++;
        for (char c : s2.toCharArray()) cnt[c - 'a']--;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (cnt[i] <= 0) continue;
            for (int j = 0; j < cnt[i]; j++) {
                sb.append((char) (i + 'a'));
            }
        }
        return sb.toString();
    }

    // 方法二：优化-词频统计
    public int minStickers2(String[] stickers, String target) {
        // 统计贴纸词频
        int n = stickers.length;
        int[][] sCnt = new int[n][26];
        for (int i = 0; i < n; i++) {
            for (char c : stickers[i].toCharArray()) {
                sCnt[i][c - 'a']++;
            }
        }
        int ans = process(sCnt, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
    // stickers[i] 数组，i号贴纸的字符统计 int[][] stickers -> 所有的贴纸
    private static int process(int[][] sCnt, String target) {
        if (target.length() == 0) return 0;
        // 统计目标字符词频
        int[] tCnt = new int[26];
        for (char c : target.toCharArray()) tCnt[c - 'a']++;
        int n = sCnt.length, min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            // 尝试第一张贴纸
            int[] sticker = sCnt[i];
            // 最关键的优化: 以包含target首字符的贴纸作为第一张(重要的剪枝!这一步也是贪心!)
            if (sticker[target.charAt(0) - 'a'] <= 0) continue;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 26; j++) {
                if (tCnt[j] <= 0) continue;
                int num = tCnt[j] - sticker[j];
                for (int k = 0; k < num; k++) {
                    sb.append((char) (j + 'a'));
                }
            }
            min = Math.min(min, process(sCnt, sb.toString()));
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    // 方法三：记忆化搜索
    public int minStickers3(String[] stickers, String target) {
        int n = stickers.length;
        int[][] sCnt = new int[n][26];
        for (int i = 0; i < n; i++) {
            for (char c : stickers[i].toCharArray()) {
                sCnt[i][c - 'a']++;
            }
        }
        Map<String, Integer> record = new HashMap<>();
        record.put("", 0);
        int ans = process(sCnt, target, record);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process(int[][] sCnt, String target, Map<String, Integer> record) {
        Integer ans = record.get(target);
        if (ans != null) return ans;
        // 统计目标字符词频
        int[] tCnt = new int[26];
        for (char c : target.toCharArray()) tCnt[c - 'a']++;
        int n = sCnt.length, min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            // 尝试第一张贴纸
            int[] sticker = sCnt[i];
            // 最关键的优化: 以包含target首字符的贴纸作为第一张(重要的剪枝!这一步也是贪心!)
            if (sticker[target.charAt(0) - 'a'] <= 0) continue;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 26; j++) {
                if (tCnt[j] <= 0) continue;
                int num = tCnt[j] - sticker[j];
                for (int k = 0; k < num; k++) {
                    sb.append((char) (j + 'a'));
                }
            }
            min = Math.min(min, process(sCnt, sb.toString(), record));
        }
        ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        record.put(target, ans);
        return ans;
    }

    public static void main(String[] args) {
        String[] stickers = {
                "heavy", "claim", "seven", "set", "had", "it", "dead", "jump", "design", "question", "sugar", "dress", "any", "special", "ground", "huge", "use", "busy", "prove", "there", "lone", "window", "trip", "also", "hot", "choose", "tie", "several", "be", "that", "corn", "after", "excite", "insect", "cat", "cook", "glad", "like", "wont", "gray", "especially", "level", "when", "cover", "ocean", "try", "clean", "property", "root", "wing"
        };
        String target = "travelbell";

        Code03_StickersToSpellWord obj = new Code03_StickersToSpellWord();
        System.out.println(obj.minStickers3(stickers, target));
    }
}
