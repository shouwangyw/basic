package com.yw.basic.designpattern.dp.L04Builder.impove;

/**
 * @author yangwei
 */
public class BuildHouseCase {
    public static void main(String[] args) {
        CommonHouse commonHouse = new CommonHouse();
        HouseDirector director = new HouseDirector(commonHouse);
        House house1 = director.build();
        System.out.println(house1);

        HighBuildingHouse highBuildingHouse = new HighBuildingHouse();
        director = new HouseDirector(highBuildingHouse);
        House house2 = director.build();
        System.out.println(house2);
    }
}
