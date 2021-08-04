package com.yw.basic.designpattern.dp.L02Factory.abstrac;

/**
 * @author yangwei
 */
public abstract class AbstractPizzaOrderFactory {
    AbstractPizzaOrderFactory(String desc) {
        System.out.println(desc);
    }

    abstract Pizza createPizza(String orderType);
}
