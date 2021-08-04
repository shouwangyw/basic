package com.yw.basic.designpattern.dp.L11Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 代理模式：CGLib动态代理
 *
 * @author yangwei
 */
public class CglibDynamicProxyCase {
    public static void main(String[] args) {
        // 创建目标对象
        StudentDao target = new StudentDao();
        // 获取代理对象，并将目标对象传递给代理对象
        StudentDao studentDao = (StudentDao) new CglibProxyFactory(target).getProxyInstance();
        // 执行代理对象的方法
        studentDao.study();
    }
}

//final class StudentDao {
class StudentDao {
    public void study() {
        System.out.println("学生学习中...");
    }
}

class CglibProxyFactory implements MethodInterceptor {
    private Object target;
    CglibProxyFactory(Object target) {
        this.target = target;
    }
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("CGLib动态代理 Before...");
        Object result = method.invoke(target, args);
        System.out.println("CGLib动态代理 After...");
        return result;
    }
    public Object getProxyInstance() {
        // 1、创建一个工具类
        Enhancer enhancer = new Enhancer();
        // 2、设置父类
        enhancer.setSuperclass(target.getClass());
        // 3、设置回调函数
        enhancer.setCallback(this);
        // 4、创建子类对象，即代理对象
        return enhancer.create();
    }
}