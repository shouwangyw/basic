package com.yw.basic.designpattern.dp.L01Singleton;

/**
 * 单例模式：枚举单例(线程安全)
 *
 * @author yangwei
 */
public class SingletonCase08 {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> System.out.println(Singleton8.getInstance().hashCode())).start();
        }
    }
}

class Singleton8 {
    private Singleton8() {
    }

    static enum SingletonInstance {
        INSTANCE;
        private Singleton8 singleton8;

        // 私有化枚举的构造函数
        private SingletonInstance() {
            singleton8 = new Singleton8();
        }

        public Singleton8 getInstance() {
            return singleton8;
        }
    }

    public static Singleton8 getInstance() {
        return SingletonInstance.INSTANCE.getInstance();
    }
}
