package com.yw.basic.designpattern.dp.L01Singleton;

/**
 * 单例模式：静态内部类单例(线程安全)
 *
 * @author yangwei
 */
public class SingletonCase07 {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> System.out.println(Singleton7.getInstance().hashCode())).start();
        }
    }
}

class Singleton7 {
    private Singleton7() {
    }

    /**
     * 使用静态内部类，在Singleton7被加载时，不会被加载
     * 只有在getInstance()时被加载，并且加载时是线程安全的
     */
    private static class SingletonInstance {
        private static final Singleton7 INSTANCE = new Singleton7();
    }

    public static Singleton7 getInstance() {
        return SingletonInstance.INSTANCE;
    }
}
