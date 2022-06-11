package com.yw.basic.mytest;

/**
 * @author yangwei
 */
public class RefCountGC {
    /**
     * 这个成员属性唯一的作用就是占用一点内存, 5MB
     */
    private byte[] bigSize = new byte[5 * 1024 * 1024];
    private Object reference = null;

    public static void main(String[] args) {
        RefCountGC obj1 = new RefCountGC();
        RefCountGC obj2 = new RefCountGC();

        obj1.reference = obj2;
        obj2.reference = obj1;

        obj1 = null;
        obj2 = null;

        // 显示的执行垃圾回收，这里发生GC，obj1和obj2能否被回收？
        System.gc();
    }
}
