package com.yw.basic.designpattern.dp.L04Builder;

/**
 * 建造者模式在JDK应用的源码分析
 *
 * @author yangwei
 */
public class JDKBuilder {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello, World");
        System.out.println(sb);
    }
}
