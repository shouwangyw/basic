package com.yw.basic.designpattern.dp.L02Factory.abstrac;

/**
 * @author yangwei
 */
public class LDCheesePizza extends Pizza {
    public LDCheesePizza() {
        super("伦敦奶酪披萨");
    }

    @Override
    public void prepare() {
        System.out.println("给制作伦敦奶酪披萨准备原材料");
    }
}
