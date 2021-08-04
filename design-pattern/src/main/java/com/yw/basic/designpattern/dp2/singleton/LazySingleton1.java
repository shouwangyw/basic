package com.yw.basic.designpattern.dp2.singleton;

/**
 * @author yangwei
 */
public class LazySingleton1 {
    private LazySingleton1() {}
    private static LazySingleton1 instance = null;

    public static LazySingleton1 getInstance() {
        if (instance == null) {
            instance = new LazySingleton1();
        }
        return instance;
    }
}
