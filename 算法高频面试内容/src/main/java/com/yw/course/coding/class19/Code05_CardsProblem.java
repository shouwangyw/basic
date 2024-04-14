package com.yw.course.coding.class19;

import java.util.LinkedList;
import java.util.List;

/**
 * 一张扑克有3个属性，每种属性有3种值（A、B、C）
 * 比如"AAA"，第一个属性值A，第二个属性值A，第三个属性值A
 * 比如"BCA"，第一个属性值B，第二个属性值C，第三个属性值A
 * 给定一个字符串类型的数组cards[]，每一个字符串代表一张扑克
 * 从中挑选三张扑克，一个属性达标的条件是：这个属性在三张扑克中全一样，或全不一样
 * 挑选的三张扑克达标的要求是：每种属性都满足上面的条件
 * 比如："ABC"、"CBC"、"BBC"
 * 第一张第一个属性为"A"、第二张第一个属性为"C"、第三张第一个属性为"B"，全不一样
 * 第一张第二个属性为"B"、第二张第二个属性为"B"、第三张第二个属性为"B"，全一样
 * 第一张第三个属性为"C"、第二张第三个属性为"C"、第三张第三个属性为"C"，全一样
 * 每种属性都满足在三张扑克中全一样，或全不一样，所以这三张扑克达标
 * 返回在cards[]中任意挑选三张扑克，达标的方法数
 *
 * @author yangwei
 **/
public class Code05_CardsProblem {

    // 方法一：暴力解
    public static int ways0(String[] cards) {
        LinkedList<String> picks = new LinkedList<>();
        return process(cards, 0, picks);
    }
    private static int process(String[] cards, int idx, LinkedList<String> picks) {
        if (picks.size() == 3) return getWays(picks);
        if (idx == cards.length) return 0;
        int ways = process(cards, idx + 1, picks);
        picks.addLast(cards[idx]);
        ways += process(cards, idx + 1, picks);
        picks.pollLast();
        return ways;
    }
    private static int getWays(List<String> picks) {
        char[] cs1 = picks.get(0).toCharArray();
        char[] cs2 = picks.get(1).toCharArray();
        char[] cs3 = picks.get(2).toCharArray();
        for (int i = 0; i < 3; i++) {
            if (cs1[i] == cs2[i] && cs2[i] == cs3[i]) continue;
            if (cs1[i] != cs2[i] && cs1[i] != cs3[i] && cs2[i] != cs3[i]) continue;
            return 0;
        }
        return 1;
    }

    // 方法二：
    public static int ways(String[] cards) {
        // 3种属性共有 3 * 3 = 27种牌面
        int n = 27;
        int[] cnts = new int[n];
        // 统计每种牌面出现的次数
        for (String card : cards) {
            char[] cs = card.toCharArray();
            // 转化成三进制
            cnts[(cs[0] - 'A') * 9 + (cs[1] - 'A') * 3 + (cs[2] - 'A')]++;
        }
        int ways = 0;
        // 统计牌面完全一样的有多少种
        for (int i = 0; i < n; i++) {
            int cnt = cnts[i];
            if (cnt > 2) ways += cnt == 3 ? 1 : (cnt * (cnt - 1) * (cnt - 2) / 6);
        }
        // 统计牌面完全不一样的有多少种
        LinkedList<Integer> path = new LinkedList<>();
        for (int i = 0; i < 27; i++) {
            if (cnts[i] == 0) continue;
            path.addLast(i);
            ways += process(cnts, n, i, path);
            path.pollLast();
        }
        return ways;
    }
    // 之前的牌面拿了一些，比如:ABC、BBB、...，那么 pre = BBB
    // 牌面一定要依次变大，所有形成的有效牌面，把方法数返回
    private static int process(int[] cnts, int n, int pre, LinkedList<Integer> path) {
        if (path.size() == 3) return getWays(cnts, path);
        int ways = 0;
        for (int next = pre + 1; next < n; next++) {
            if (cnts[next] == 0) continue;
            path.addLast(next);
            ways += process(cnts, n, next, path);
            path.pollLast();
        }
        return ways;
    }
    private static int getWays(int[] cnts, List<Integer> path) {
        int v1 = path.get(0), v2 = path.get(1), v3 = path.get(2);
        for (int i = 9; i > 0; i /= 3) {
            int cur1 = v1 / i, cur2 = v2 / i, cur3 = v3 / i;
            v1 %= i;
            v2 %= i;
            v3 %= i;
            if (cur1 == cur2 && cur2 == cur3) continue;
            if (cur1 != cur2 && cur1 != cur3 && cur2 != cur3) continue;
            return 0;
        }
        v1 = path.get(0);
        v2 = path.get(1);
        v3 = path.get(2);
        return cnts[v1] * cnts[v2] * cnts[v3];
    }

    public static void main(String[] args) {
        int size = 20;
        int testTime = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            String[] arr = generateCards(size);
            int ans1 = ways0(arr);
            int ans2 = ways(arr);
            if (ans1 != ans2) {
                for (String str : arr) {
                    System.out.println(str);
                }
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("test finish");

        long start = 0;
        long end = 0;
        String[] arr = generateCards(10000000);
        System.out.println("arr size : " + arr.length + " runtime test begin");
        start = System.currentTimeMillis();
        ways(arr);
        end = System.currentTimeMillis();
        System.out.println("run time : " + (end - start) + " ms");
        System.out.println("runtime test end");
    }
    private static String[] generateCards(int size) {
        int n = (int) (Math.random() * size) + 3;
        String[] ans = new String[n];
        for (int i = 0; i < n; i++) {
            char cha0 = (char) ((int) (Math.random() * 3) + 'A');
            char cha1 = (char) ((int) (Math.random() * 3) + 'A');
            char cha2 = (char) ((int) (Math.random() * 3) + 'A');
            ans[i] = String.valueOf(cha0) + cha1 + cha2;
        }
        return ans;
    }
}
