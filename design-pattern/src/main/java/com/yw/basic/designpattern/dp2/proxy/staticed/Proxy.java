package com.yw.basic.designpattern.dp2.proxy.staticed;

/**
 * 代理类
 */
public class Proxy extends Subject {
    private RealSubject realSubject = new RealSubject();

    @Override
    public void request() {
        preRequest();
        realSubject.request();
        postRequest();
    }

    private void preRequest() {
        System.out.println("before request ...");
    }

    private void postRequest() {
        System.out.println("after request ...");
    }
}