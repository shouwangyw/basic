package com.yw.basic.designpattern.dp2.singleton;

/**
 * 枚举实现单例
 *
 * @author yangwei
 */
public class EnumSingleton {
    // 私有化构造函数
    private EnumSingleton() {
    }

    static enum SingletonEnum {
        //创建一个枚举对象，该对象天生为单例
        INSTANCE;

        private EnumSingleton singleton;

        // 私有化枚举的构造函数
        private SingletonEnum() {
            singleton = new EnumSingleton();
        }

        public EnumSingleton getInstance() {
            return singleton;
        }
    }

    // 对外暴露一个获取User对象的静态方法
    public static EnumSingleton getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }
}
