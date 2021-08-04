package com.yw.basic.designpattern.dp2.decorate;

/**
 * 具体的装饰类
 */
public class ConcreteDecorator extends Decorator {
    public ConcreteDecorator(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        beforeOperation();
        super.operation(); // 调用原有业务方法
        afterOperation();
    }

    // 新增业务方法
    public void beforeOperation() {
        System.out.println("before original operation");
    }

    public void afterOperation() {
        System.out.println("after original operation");
    }
}