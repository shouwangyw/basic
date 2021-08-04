package com.yw.basic.designpattern.dp2.singleton;

/**
 * 单例模式：同时在内存中，只有一个对象存在
 * 如何保证一个类在内存中只能有一个实例呢？
 * 1、构造私有
 * 2、使用私有静态成员变量初始化本身对象
 * 3、对外提供静态公共方法获取本身对象
 *
 * @author yangwei
 */
public class HungrySingleton {
    // 1、构造私有
    private HungrySingleton() {
    }

    // 2、成员变量初始化本身对象
    private static HungrySingleton singleton = new HungrySingleton();

    // 3、对外提供公共方法获取对象
    public static HungrySingleton getInstance() {
        return singleton;
    }

    public void sayHello(String name) {
        System.out.println("Hello," + name);
    }
}
