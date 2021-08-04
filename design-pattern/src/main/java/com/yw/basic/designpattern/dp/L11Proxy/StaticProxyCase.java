package com.yw.basic.designpattern.dp.L11Proxy;

/**
 * 代理模式：静态代理
 *
 * @author yangwei
 */
public class StaticProxyCase {
    public static void main(String[] args) {
        // 创建被代理对象
        TeacherDao teacherDao = new TeacherDao();
        // 创建代理对象，同时将被代理对象传递给代理对象
        TeacherDaoProxy proxy = new TeacherDaoProxy(teacherDao);
        // 通过代理对象，调用到被代理对象的方法
        proxy.teach();
    }
}

/**
 * 代理对象
 */
class TeacherDaoProxy implements ITeacherDao {
    private ITeacherDao target;
    TeacherDaoProxy(ITeacherDao target) {
        this.target = target;
    }
    @Override
    public void teach() {
        System.out.println("开始代理...");
        target.teach();
        System.out.println("结束代理...");
    }
}
