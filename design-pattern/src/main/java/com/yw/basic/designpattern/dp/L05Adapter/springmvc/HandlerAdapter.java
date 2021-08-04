package com.yw.basic.designpattern.dp.L05Adapter.springmvc;

/**
 * @author yangwei
 */
public interface HandlerAdapter {
    boolean support(Handler handler);
    void handle(Handler handler);
}

class SimpleHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean support(Handler handler) {
        return handler instanceof SimpleHandler;
    }
    @Override
    public void handle(Handler handler) {
        handler.doHandle();
    }
}

class HttpHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean support(Handler handler) {
        return handler instanceof HttpHandler;
    }
    @Override
    public void handle(Handler handler) {
        handler.doHandle();
    }
}

class AnnotationHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean support(Handler handler) {
        return handler instanceof AnnotationHandler;
    }
    @Override
    public void handle(Handler handler) {
        handler.doHandle();
    }
}