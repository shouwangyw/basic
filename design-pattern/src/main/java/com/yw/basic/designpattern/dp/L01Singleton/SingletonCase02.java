package com.yw.basic.designpattern.dp.L01Singleton;

/**
 * 单例模式：饿汉式(静态代码块)
 *
 * @author yangwei
 */
public class SingletonCase02 {
    public static void main(String[] args) {
        Singleton2 instance1 = Singleton2.getInstance();
        Singleton2 instance2 = Singleton2.getInstance();
        System.out.println(instance1 == instance2);
    }
}

class Singleton2 {
    /**
     * 1、构造器私有化(防止 new)
     */
    private Singleton2() {
    }

    /**
     * 2、类的内部创建对象：在静态代码块执行时，创建单例对象
     */
    private static Singleton2 INSTANCE;

    static {
        INSTANCE = new Singleton2();
    }

    /**
     * 3、向外暴露一个静态的公共方法
     */
    public static Singleton2 getInstance() {
        return INSTANCE;
    }
}