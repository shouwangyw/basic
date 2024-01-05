package com.yw.course.advance.class14;

import java.util.*;

/**
 * @author yangwei
 */
public class Code05_UnionFind {

    public static class UnionFind<V> {
        Map<V, V> parentMap;
        Map<V, Integer> sizeMap;

        public UnionFind(V... values) {
            this(Arrays.asList(values));
        }
        public UnionFind(List<V> values) {
            parentMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V x : values) {
                parentMap.put(x, x);
                sizeMap.put(x, 1);
            }
        }

        // 给你一个节点，请你往上到不能再往上，把代表返回
        private V findFather(V cur) {
            Stack<V> path = new Stack<>();
            while (cur != parentMap.get(cur)) {
                path.push(cur);
                cur = parentMap.get(cur);
            }
            // 路径压缩
            while (!path.isEmpty()) parentMap.put(path.pop(), cur);
            return cur;
        }

        public boolean isSameSet(V a, V b) {
            return findFather(a) == findFather(b);
        }

        public void union(V a, V b) {
            V fa = findFather(a);
            V fb = findFather(b);
            if (fa == fb) return;
            int sa = sizeMap.get(fa);
            int sb = sizeMap.get(fb);
            V big = sa >= sb ? fa : fb;
            V small = big == fa ? fb : fa;
            parentMap.put(small, big);
            sizeMap.put(big, sa + sb);
            sizeMap.remove(small);
        }

        public int size() {
            return sizeMap.size();
        }
    }

}
