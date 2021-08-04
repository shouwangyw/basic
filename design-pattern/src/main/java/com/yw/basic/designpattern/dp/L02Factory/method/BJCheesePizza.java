package com.yw.basic.designpattern.dp.L02Factory.method;

/**
 * @author yangwei
 */
public class BJCheesePizza extends Pizza {
    public BJCheesePizza() {
        super("北京奶酪披萨");
    }

    @Override
    public void prepare() {
        System.out.println("给制作北京奶酪披萨准备原材料");
    }
}
