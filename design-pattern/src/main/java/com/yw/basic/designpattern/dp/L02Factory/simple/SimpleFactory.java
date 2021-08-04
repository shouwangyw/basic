package com.yw.basic.designpattern.dp.L02Factory.simple;

/**
 * 简单工厂类
 * @author yangwei
 */
public class SimpleFactory {
    public Pizza createPizza(String orderType) {
        Pizza pizza = null;
        if (orderType.equals("greek")) {
            pizza = new GreekPizza();
        } else if (orderType.equals("cheese")) {
            pizza = new CheesePizza();
        }
        if (pizza != null) {
            // 输出披萨制作过程
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
        }
        return pizza;
    }
}
