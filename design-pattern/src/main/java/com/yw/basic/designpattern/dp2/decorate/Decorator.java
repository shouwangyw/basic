package com.yw.basic.designpattern.dp2.decorate;

/**
 * 抽象装饰类
 */
public class Decorator extends Component {
    // 持有一个抽象构件对象的引用
    private Component component;

    // 构造函数注入一个抽象构件类型的对象
    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        component.operation(); // 调用原有的业务方法
    }
}