package com.yw.basic.designpattern.dp.L04Builder.house;

/**
 * @author yangwei
 */
public class CommonHouse extends AbstractHouse {
    @Override
    void buildBasic() {
        System.out.println("普通房子 打地基");
    }

    @Override
    void buildWalls() {
        System.out.println("普通房子 砌墙");
    }

    @Override
    void roofed() {
        System.out.println("普通房子 盖屋顶");
    }
}
