package com.yw.basic.designpattern.dp.L01Singleton;

/**
 * 单例模式：懒汉式(线程不安全)
 *
 * @author yangwei
 */
public class SingletonCase03 {
    public static void main(String[] args) {
//        Singleton3 instance1 = Singleton3.getInstance();
//        Singleton3 instance2 = Singleton3.getInstance();
//        System.out.println(instance1 == instance2);
        for (int i = 0; i < 50; i++) {
            new Thread(() -> System.out.println(Singleton3.getInstance().hashCode())).start();
        }
    }
}

class Singleton3 {
    private Singleton3() {
    }

    private static Singleton3 INSTANCE;

    /**
     * 提供一个静态的公有方法，当使用到该方法时，才去创建 instance
     */
    public static Singleton3 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton3();
        }
        return INSTANCE;
    }
}
