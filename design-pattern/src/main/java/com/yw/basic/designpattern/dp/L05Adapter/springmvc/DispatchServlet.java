package com.yw.basic.designpattern.dp.L05Adapter.springmvc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class DispatchServlet {
    private static List<HandlerAdapter> handlerAdapters = new ArrayList<>();
    public DispatchServlet() {
        handlerAdapters.add(new SimpleHandlerAdapter());
        handlerAdapters.add(new HttpHandlerAdapter());
        handlerAdapters.add(new AnnotationHandlerAdapter());
    }
    public void doDispatch() {
        HttpHandler handler = new HttpHandler();
        HandlerAdapter adapter = getHandler(handler);
        if (adapter != null) {
            adapter.handle(handler);
        }
    }

    private HandlerAdapter getHandler(Handler handler) {
        for (HandlerAdapter adapter : handlerAdapters) {
            if (adapter.support(handler)) {
                return adapter;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        new DispatchServlet().doDispatch();
    }
}
