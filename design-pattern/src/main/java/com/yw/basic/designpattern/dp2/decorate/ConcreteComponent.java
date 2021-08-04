package com.yw.basic.designpattern.dp2.decorate;

/**
 * 具体构件类
 */
public class ConcreteComponent extends Component {
    @Override
    public void operation() {
        // 构件的基本功能
        System.out.println("ConcreteComponent's basic function");
    }
}