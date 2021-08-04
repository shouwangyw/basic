package com.yw.basic.designpattern.dp.L02Factory.pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 披萨订购
 *
 * @author yangwei
 */
public class PizzaOrder {
    Pizza pizza = null;
    public PizzaOrder() {
        String orderType = "";
        do {
            orderType = getType();
            if (orderType.equals("greek")) {
                pizza = new GreekPizza();
            } else if (orderType.equals("cheese")) {
                pizza = new CheesePizza();
            } else {
                break;
            }
            // 输出披萨制作过程
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
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
