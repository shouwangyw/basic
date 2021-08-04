package com.yw.basic.designpattern.dp.L02Factory.pizza;

/**
 * @author yangwei
 */
public class GreekPizza extends Pizza {
    public GreekPizza() {
        super("希腊披萨");
    }
    @Override
    public void prepare() {
        System.out.println("给希腊披萨准备原材料");
    }
}
