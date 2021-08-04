package com.yw.basic.designpattern.dp.L02Factory.abstrac;

/**
 * @author yangwei
 */
public class PizzaStore {
    public static void main(String[] args) {
        new PizzaOrder(new BJPizzaOrderFactory());
        new PizzaOrder(new LDPizzaOrderFactory());
    }
}
