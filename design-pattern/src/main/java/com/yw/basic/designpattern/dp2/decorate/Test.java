package com.yw.basic.designpattern.dp2.decorate;

/**
 * 测试
 */
public class Test {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        component = new ConcreteDecorator(component);
        component.operation();
//		输出结果：
//		before original operation
//		ConcreteComponent's basic function
//		after original operation
    }
}