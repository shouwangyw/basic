package com.yw.basic.designpattern.principle.p01;

/**
 * 单一职责原则
 *
 * @author yangwei
 */
public class SingleResponsibilityCase {
    /**
     * 方式一：违反了单一职责原则
     * 解决方案：根据交通工具的不同，分解成不同的类
     */
    static class VehicleUtil {
        static void run(String vehicle) {
            System.out.println(vehicle + " 在公路上跑...");
        }
    }

    /**
     * 方式二：遵循单一职责原则，即将类分解，同时修改客户端
     */
    static class RoadVehicleUtil {
        static void run(String vehicle) {
            System.out.println(vehicle + " 在公路上跑...");
        }
    }
    static class AirVehicleUtil {
        static void run(String vehicle) {
            System.out.println(vehicle + " 在天空中飞行...");
        }
    }

    /**
     * 方式三：没有对原有的类做大的修改，在类上没有遵循单一职责原则，但是在方法上遵循单一职责原则
     */
    static class VehicleUtil2 {
        static void run(String vehicle) {
            System.out.println(vehicle + " 在公路上跑...");
        }

        static void runAir(String vehicle) {
            System.out.println(vehicle + " 在天空中飞行...");
        }

        static void runWater(String vehicle) {
            System.out.println(vehicle + " 在水中运行...");
        }
    }

    public static void main(String[] args) {
        System.out.println("方式一：");
        VehicleUtil.run("汽车");
        VehicleUtil.run("摩托车");
        VehicleUtil.run("飞机");

        System.out.println("方式二：");
        RoadVehicleUtil.run("汽车");
        RoadVehicleUtil.run("摩托车");
        AirVehicleUtil.run("飞机");

        System.out.println("方式三：");
        VehicleUtil2.run("汽车");
        VehicleUtil2.runWater("轮船");
        VehicleUtil2.runAir("飞机");
    }
}

