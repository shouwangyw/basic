package com.yw.basic.designpattern.dp.L01Singleton;

/**
 * 单例模式：双重检查单例(线程安全)
 *
 * @author yangwei
 */
public class SingletonCase06 {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> System.out.println(Singleton6.getInstance().hashCode())).start();
        }
    }
}

class Singleton6 {
    private Singleton6() {
    }

    /**
     * 这里的 volatile 一定不能够少，防止获取到半初始化状态的实例
     */
    private static volatile Singleton6 INSTANCE;

    /**
     * 加synchronized，解决多线程不安全问题
     */
    public static Singleton6 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton6.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton6();
                }
            }
        }
        return INSTANCE;
    }
}
