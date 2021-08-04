package com.yw.basic.designpattern.dp.L02Factory.method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author yangwei
 */
public abstract class PizzaOrder {
    public PizzaOrder(String desc) {
        System.out.println(desc);
        do {
            Pizza pizza = createPizza(getType());
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
     * 交给工厂子类完成
     */
    abstract Pizza createPizza(String orderType);

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
