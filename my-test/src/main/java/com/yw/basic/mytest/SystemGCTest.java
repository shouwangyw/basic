package com.yw.basic.mytest;

/**
 * @author yangwei
 */
public class SystemGCTest {
    public static void main(String[] args) {
        new SystemGCTest();
        // 提醒 JVM 的垃圾回收器执行 GC，但是不确定是否马上执行GC
        // 与 Runtime.getRuntime().gc();的作用一样
        System.gc();
        
        // 强制调用使用引用的对象的 finalize() 方法
        System.runFinalization();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("System GC Test 重写了finalize()");
    }
}
