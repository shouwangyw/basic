package com.yw.basic.designpattern.dp.L02Factory.method;

/**
 * @author yangwei
 */
public class BJPepperPizza extends Pizza {
    public BJPepperPizza() {
        super("北京胡椒披萨");
    }

    @Override
    public void prepare() {
        System.out.println("给制作北京胡椒披萨准备原材料");
    }
}
