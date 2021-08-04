package com.yw.basic.designpattern.dp2.singleton;

/**
 * @author yangwei
 */
public class LazySingleton4 {
    private LazySingleton4() {
    }

    /**
     * 此处使用一个内部类来维护单例 JVM在类加载的时候，是互斥的，所以可以由此保证线程安全问题
     */
    private static class SingletonFactory {
        private static LazySingleton4 instance = new LazySingleton4();
    }

    public static LazySingleton4 getInstance() {
        return SingletonFactory.instance;
    }
}
