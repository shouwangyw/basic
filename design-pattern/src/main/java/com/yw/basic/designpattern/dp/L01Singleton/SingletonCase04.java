package com.yw.basic.designpattern.dp.L01Singleton;

/**
 * 单例模式：懒汉式(线程安全，同步方法)
 *
 * @author yangwei
 */
public class SingletonCase04 {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> System.out.println(Singleton4.getInstance().hashCode())).start();
        }
    }
}

class Singleton4 {
    private Singleton4() {
    }

    private static Singleton4 INSTANCE;

    /**
     * 加synchronized，解决多线程不安全问题
     */
    public static synchronized Singleton4 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton4();
        }
        return INSTANCE;
    }
}