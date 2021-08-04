package com.yw.basic.designpattern.dp.L02Factory.simple;

/**
 * @author yangwei
 */
public class CheesePizza extends Pizza {
    public CheesePizza() {
        super("奶酪披萨");
    }
    @Override
    public void prepare() {
        System.out.println("给制作奶酪披萨准备原材料");
    }
}
