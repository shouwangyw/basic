package com.yw.basic.designpattern.dp.L02Factory.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 披萨订购
 *
 * @author yangwei
 */
public class PizzaOrder {
    SimpleFactory simpleFactory;

    public PizzaOrder(SimpleFactory simpleFactory) {
        do {
            this.simpleFactory = simpleFactory;
            simpleFactory.createPizza(getType());
        } while (true);
    }

    /**
     * 获取客户希望订购披萨的种类
     */
    private String getType() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza type: ");
            return in.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
