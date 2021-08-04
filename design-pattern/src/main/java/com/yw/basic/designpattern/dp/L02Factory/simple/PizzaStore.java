package com.yw.basic.designpattern.dp.L02Factory.simple;

/**
 * @author yangwei
 */
public class PizzaStore {
    public static void main(String[] args) {
        new PizzaOrder(new SimpleFactory());
    }
}
