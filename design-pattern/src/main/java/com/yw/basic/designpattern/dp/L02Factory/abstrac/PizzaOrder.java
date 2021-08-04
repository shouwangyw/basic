package com.yw.basic.designpattern.dp.L02Factory.abstrac;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author yangwei
 */
public class PizzaOrder {
    private AbstractPizzaOrderFactory factory;

    PizzaOrder(AbstractPizzaOrderFactory factory) {
        this.factory = factory;
        do {
            Pizza pizza = this.factory.createPizza(getType());
            if (pizza != null) {
                // 输出披萨制作过程
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            } else {
                System.out.println("没有这种披萨，订购失败");
                break;
            }
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
