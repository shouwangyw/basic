package com.yw.basic.designpattern.dp.L21Strategy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 策略模式在JDK-Arrays 应用的源码分析
 *
 * @author yangwei
 */
public class JDKStrategy {
    public static void main(String[] args) {
        Integer[] data = {9, 1, 2, 8, 4, 3};
        // 实现了 Comparator 接口(策略接口) , 匿名类 对象 new Comparator<Integer>(){..}
        // 对象 new Comparator<Integer>(){..} 就是实现了 策略接口 的对象
        // public int compare(Integer o1, Integer o2){} 指定具体的处理方式
        Comparator<Integer> comparator = (o1, o2) -> (o1 > o2) ? 1 : -1;
        Arrays.sort(data, comparator);
        System.out.println(Arrays.toString(data));
    }
}
