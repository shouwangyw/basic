package com.yw.basic.designpattern.dp2.proxy.dynamiced;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 */
public class JDKProxyFactory implements InvocationHandler {
    // 目标对象的引用
    private Object target;

    // 通过构造函数将目标对象注入到代理对象中
    public JDKProxyFactory(Object target) {
        super();
        this.target = target;
    }

    public Object getProxy() {
        // 如何生成一个代理类呢？
        // 1、编写源文件(java文件)----目录类接口interface实现类（调用了目标对象的方法）
        // 2、编译源文件为class文件
        // 3、将class文件加载到JVM中(ClassLoader)
        // 4、将class文件对应的对象进行实例化（反射）

        // Proxy是JDK中的API类
        // 第一个参数：目标对象的类加载器
        // 第二个参数：目标对象的接口
        // 第三个参数：代理对象的执行处理器
        Object object = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        return object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method method2 = target.getClass().getMethod("saveUser", null);
        Method method3 = Class.forName("com.sun.proxy.$Proxy2").getMethod("saveUser", null);
        System.out.println("目标对象的方法:" + method2.toString());
        System.out.println("目标接口的方法:" + method.toString());
        System.out.println("代理对象的方法:" + method3.toString());
        System.out.println("这是jdk的代理方法");
        // 下面的代码，是反射中的API用法
        // 该行代码，实际调用的是[目标对象]的方法
        // 利用反射，调用[目标对象]的方法
        Object returnValue = method.invoke(target, args);
        return returnValue;
    }
}