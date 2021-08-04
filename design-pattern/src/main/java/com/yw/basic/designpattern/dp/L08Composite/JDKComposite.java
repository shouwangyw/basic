package com.yw.basic.designpattern.dp.L08Composite;

import java.util.HashMap;
import java.util.Map;

/**
 * 组合模式在JDK集合的源码分析:HashMap
 *
 * @author yangwei
 */
public class JDKComposite {
    public static void main(String[] args) {
        /*
        Map 就是一个抽象的构建（类似组合模式里面的Component）
        HashMap是一个中间的构建（Composite），实现/继承了相关方法：put、putAll
        Node 是 HasMap 的静态内部类，类似于 Leaf 叶子结点，没有 put、putAll 方法
        */
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap.put(0, "西游记");

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "水浒传");
        map.put(2, "红楼梦");
        hashMap.putAll(map);
        System.out.println(hashMap);
    }
}
