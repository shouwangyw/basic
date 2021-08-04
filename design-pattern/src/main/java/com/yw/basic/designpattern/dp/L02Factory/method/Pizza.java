package com.yw.basic.designpattern.dp.L02Factory.method;

/**
 * 定义一个披萨的抽象类
 * @author yangwei
 */
public abstract class Pizza {
    /**
     * 披萨名字
     */
    protected String name;
    /**
     * 原材料准备
     */
    public abstract void prepare();
    public void bake(){
        System.out.println(name + " baking;");
    }
    public void cut() {
        System.out.println(name + " cutting;");
    }
    public void box() {
        System.out.println(name + " boxing;");
    }
    public Pizza(String name) {
        this.name = name;
    }
}
