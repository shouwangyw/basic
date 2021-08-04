package com.yw.basic.designpattern.dp.L03Prototype;

/**
 * 原型模式：深浅复制
 *
 * @author yangwei
 */
public class PrototypeCase {
    public static void main(String[] args) throws Exception {
        // 创建原对象
        DeepShallowCopy prototype = new DeepShallowCopy();
        prototype.setString("zhangsan");
        prototype.setObj(new SerializableObject());

        System.out.println("克隆之前的对象：" + prototype.getString());
        System.out.println("克隆之前的对象：" + prototype.getObj());
        // 浅复制出来的对象
        DeepShallowCopy shallowClone = (DeepShallowCopy) prototype.shallowClone();
        System.out.println("浅复制出来的对象：" + shallowClone.getString());
        System.out.println("浅复制出来的对象：" + shallowClone.getObj());

        // 深复制出来的对象
        DeepShallowCopy deepClone = (DeepShallowCopy) prototype.deepClone();
        System.out.println("深复制出来的对象：" + deepClone.getString());
        System.out.println("深复制出来的对象：" + deepClone.getObj());
    }
}
