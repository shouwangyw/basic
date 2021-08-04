package com.yw.basic.designpattern.dp2.adapter;

/**
 * 类适配器类
 */
public class ClassAdapter extends Adaptee implements Target {
    public void method2() {
        System.out.println("This is Target's method");
    }
}