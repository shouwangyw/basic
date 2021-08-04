package com.yw.basic.designpattern.dp.L02Factory.abstrac;

/**
 * @author yangwei
 */
public class BJPizzaOrderFactory extends AbstractPizzaOrderFactory {
    BJPizzaOrderFactory() {
        super("开始订购北京披萨");
    }

    @Override
    Pizza createPizza(String orderType) {
        if (orderType.equals("cheese")) {
            return new BJCheesePizza();
        } else if (orderType.equals("pepper")) {
            return new BJPepperPizza();
        }
        return null;
    }
}
