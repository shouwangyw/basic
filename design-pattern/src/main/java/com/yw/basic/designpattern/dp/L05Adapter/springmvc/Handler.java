package com.yw.basic.designpattern.dp.L05Adapter.springmvc;

/**
 * @author yangwei
 */
public interface Handler {
    void doHandle();
}

class SimpleHandler implements Handler {
    @Override
    public void doHandle() {
        System.out.println("SimpleHandler.doHandle() ...");
    }
}

class HttpHandler implements Handler {
    @Override
    public void doHandle() {
        System.out.println("HttpHandler.doHandle() ...");
    }
}

class AnnotationHandler implements Handler {
    @Override
    public void doHandle() {
        System.out.println("AnnotationHandler.doHandle() ...");
    }
}