package com.yw.basic.designpattern.dp.L01Singleton;

/**
 * 单例模式：饿汉式(静态常量)
 *
 * @author yangwei
 */
public class SingletonCase01 {
    public static void main(String[] args) {
        Singleton1 instance1 = Singleton1.getInstance();
        Singleton1 instance2 = Singleton1.getInstance();
        System.out.println(instance1 == instance2);
    }
}

class Singleton1 {
    /**
     * 1、构造器私有化(防止 new)
     */
    private Singleton1() {
    }

    /**
     * 2、类的内部创建对象
     */
    private static final Singleton1 INSTANCE = new Singleton1();

    /**
     * 3、向外暴露一个静态的公共方法
     */
    public static Singleton1 getInstance() {
        return INSTANCE;
    }
}
