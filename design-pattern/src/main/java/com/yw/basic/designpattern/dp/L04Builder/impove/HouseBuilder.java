package com.yw.basic.designpattern.dp.L04Builder.impove;

/**
 * @author yangwei
 */
public abstract class HouseBuilder {
    protected House house = new House();
    abstract void buildBasic();
    abstract void buildWalls();
    abstract void roofed();
    public House getHouse() {
        return house;
    }
}
