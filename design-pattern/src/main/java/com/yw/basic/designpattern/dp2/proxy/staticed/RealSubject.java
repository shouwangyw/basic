package com.yw.basic.designpattern.dp2.proxy.staticed;

/**
 * 真实主题
 */
public class RealSubject extends Subject {
    @Override
    public void request() {
        System.out.println("RealSubject request ...");
    }
}