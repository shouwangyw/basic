package com.yw.basic.designpattern.dp.L04Builder.impove;

/**
 * @author yangwei
 */
public class CommonHouse extends HouseBuilder {
    @Override
    void buildBasic() {
        System.out.println("普通房子 打地基 5 米");
        house.setBasic("5 米");
    }

    @Override
    void buildWalls() {
        System.out.println("普通房子 砌墙 10 厘米");
        house.setWall("10 厘米");
    }

    @Override
    void roofed() {
        System.out.println("普通房子屋顶");
        house.setRoofed("普通屋顶");
    }
}
