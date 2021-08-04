package com.yw.basic.designpattern.dp.L04Builder.impove;

/**
 * @author yangwei
 */
public class HighBuildingHouse extends HouseBuilder {
    @Override
    void buildBasic() {
        System.out.println("高楼 打地基 100 米");
        house.setBasic("100 米");
    }

    @Override
    void buildWalls() {
        System.out.println("高楼 砌墙 25 厘米");
        house.setWall("25 厘米");
    }

    @Override
    void roofed() {
        System.out.println("高楼屋顶");
        house.setRoofed("高楼屋顶");
    }
}
