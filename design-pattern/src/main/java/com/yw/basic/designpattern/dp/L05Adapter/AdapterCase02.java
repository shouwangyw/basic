package com.yw.basic.designpattern.dp.L05Adapter;

/**
 * @author yangwei
 */
public class AdapterCase02 {
    public static void main(String[] args) {
        AbstractAadpter adpter = new AbstractAadpter() {
            @Override
            public void m1() {
                System.out.println("m1");
            }
        };
        adpter.m1();
    }
}

interface InterfaceA {
    void m1();
    void m2();
    void m3();
    void m4();
}

abstract class AbstractAadpter implements InterfaceA {
    @Override public void m1() {}
    @Override public void m2() {}
    @Override public void m3() {}
    @Override public void m4() {}
}
