package com.yw.basic.designpattern.dp.L04Builder.house;

/**
 * @author yangwei
 */
public abstract class AbstractHouse {
    abstract void buildBasic();
    abstract void buildWalls();
    abstract void roofed();
    public void build() {
        buildBasic();
        buildWalls();
        roofed();
        System.out.println("房子建造完成~~");
    }
}
