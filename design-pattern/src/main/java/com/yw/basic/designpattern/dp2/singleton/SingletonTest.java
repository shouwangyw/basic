package com.yw.basic.designpattern.dp2.singleton;

/**
 * @author yangwei
 */
public class SingletonTest {
    public static void main(String[] args) {
        // 饿汉式
        HungrySingleton instance1 = HungrySingleton.getInstance();
        HungrySingleton instance2 = HungrySingleton.getInstance();
        if (instance1 == instance2) {
            System.out.println("两个对象是相同实例");
        } else {
            System.out.println("两个对象是不同实例");
        }

        LazySingleton4 instance3 = LazySingleton4.getInstance();
        LazySingleton4 instance4 = LazySingleton4.getInstance();
        if (instance3 == instance4) {
            System.out.println("两个对象是相同实例"); // ^_^
        } else {
            System.out.println("两个对象是不同实例");
        }
    }
}
