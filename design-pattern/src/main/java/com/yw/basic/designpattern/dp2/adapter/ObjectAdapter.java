package com.yw.basic.designpattern.dp2.adapter;

/**
 * 对象适配器类
 */
public class ObjectAdapter implements Target {
    private Adaptee adaptee;

    public ObjectAdapter(Adaptee adaptee) {
        super();
        this.adaptee = adaptee;
    }

    public void method1() {
        adaptee.method1();
    }

    public void method2() {
        System.out.println("This is Target's method");
    }
}