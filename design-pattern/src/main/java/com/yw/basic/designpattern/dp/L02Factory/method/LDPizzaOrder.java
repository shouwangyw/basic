package com.yw.basic.designpattern.dp.L02Factory.method;

/**
 * @author yangwei
 */
public class LDPizzaOrder extends PizzaOrder {
    LDPizzaOrder() {
        super("开始订购伦敦披萨");
    }
    @Override
    Pizza createPizza(String orderType) {
        if (orderType.equals("cheese")) {
            return new LDCheesePizza();
        } else if (orderType.equals("pepper")) {
            return new LDPepperPizza();
        }
        return null;
    }
}
