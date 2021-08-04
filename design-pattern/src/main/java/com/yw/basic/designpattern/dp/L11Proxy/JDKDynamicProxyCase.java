package com.yw.basic.designpattern.dp.L11Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理模式：JDK动态代理
 *
 * @author yangwei
 */
public class JDKDynamicProxyCase {
    public static void main(String[] args) {
        JDKProxyFactory proxy = new JDKProxyFactory(new TeacherDao());
        ITeacherDao teacherDao = (ITeacherDao) proxy.getProxyInstance();
        System.out.println("teacherDao = " + teacherDao + ", teacherDao.getClass = " + teacherDao.getClass());
        // class com.atguigu.dp.L11Proxy.$Proxy0 内存中动态生成了代理对象
        teacherDao.teach();
    }
}

class JDKProxyFactory {
    private Object target;

    JDKProxyFactory(Object target) {
        this.target = target;
    }

    /**
     * public static Object newProxyInstance(ClassLoader loader,
     * Class<?>[] interfaces,
     * InvocationHandler h)
     * 1、loader：指定当前目标对象使用的类加载器，获取加载器的方法固定
     * 2、interfaces：目标对象实现的接口类型，使用泛型确定类型
     * 3、h：事件处理，执行目标对象方法时，会触发事情处理器方法，会把当前执行的目标对象方法作为参数传入
     */
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("JDK动态代理 Before...");
                        Object result = method.invoke(target, args);
                        System.out.println("JDK动态代理 After...");
                        return result;
                    }
                });
    }
}
