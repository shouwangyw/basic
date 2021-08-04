package com.yw.basic.designpattern.dp.L01Singleton;

/**
 * 单例模式：懒汉式(线程安全，同步代码块)
 *
 * @author yangwei
 */
public class SingletonCase05 {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> System.out.println(Singleton5.getInstance().hashCode())).start();
        }
    }
}

class Singleton5 {
    private Singleton5() {
    }

    private static Singleton5 INSTANCE;

    /**
     * 加synchronized，解决多线程不安全问题
     */
    public static Singleton5 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton5.class) {
                INSTANCE = new Singleton5();
            }
        }
        return INSTANCE;
    }
}
