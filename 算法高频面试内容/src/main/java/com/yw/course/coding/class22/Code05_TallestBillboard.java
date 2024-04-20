package com.yw.course.coding.class22;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试链接: https://leetcode.cn/problems/tallest-billboard/
 *
 * @author yangwei
 */
public class Code05_TallestBillboard {

    public int tallestBillboard(int[] rods) {
        // key: 集合对的差值，value: 满足差值key的集合对中，最好的一对(较小集合的累加和)
        Map<Integer, Integer> dp = new HashMap<>(), cur;
        dp.put(0, 0); // 空集 对 空集
        for (int x : rods) {
            if (x == 0) continue;
            // cur 内部数据完全和dp一样，考虑x之前的集合差值状况拷贝下来
            cur = new HashMap<>(dp);
            for (int d : cur.keySet()) {
                int diffBest = cur.get(d);
                dp.put(d + x, Math.max(diffBest, dp.getOrDefault(d + x, 0)));
                // x决定放入，比较小的那个，新的差值 Math.abs(x - d)
                // 之前差值为Math.abs(x - d)，的那一对，就要和这一对，决策一下
                // 之前那一对，较小集合的累加和diffXd
                int diffXd = dp.getOrDefault(Math.abs(x - d), 0);
                // x决定放入比较小的那个, 但是放入之后，没有超过这一对较大的那个
                if (d >= x) dp.put(d - x, Math.max(diffBest + x, diffXd));
                else dp.put(x - d, Math.max(diffBest + d, diffXd));
            }
        }
        return dp.get(0);
    }

}
