package com.yw.basic.designpattern.dp.L05Adapter;

/**
 * 适配器模式: 类适配器、对象适配器
 *
 * @author yangwei
 */
public class AdapterCase01 {
    public static void main(String[] args) {
        new Phone().charging(new ClassVoltageAdapter());
        new Phone().charging(new ObjectVoltageAdapter(new Voltage220V()));
    }
}

/**
 * 被适配的类
 */
class Voltage220V {
    public int output220V() {
        int src = 220;
        System.out.println("电压" + src + "V");
        return src;
    }
}

/**
 * 适配接口
 */
interface IVoltage5V {
    int output5V();
}

/**
 * 手机
 */
class Phone {
    void charging(IVoltage5V voltage5V) {
        int output = voltage5V.output5V();
        if (output == 5) {
            System.out.println("电压为5V，可以充电");
        } else if (output > 5) {
            System.out.println("电压大于5V，不可以充电");
        }
    }
}

/**
 * 类适配器
 */
class ClassVoltageAdapter extends Voltage220V implements IVoltage5V {
    @Override
    public int output5V() {
        System.out.println("----类适配器----");
        int srcV = output220V();
        int dstV = srcV / 44;
        return dstV;
    }
}

/**
 * 对象适配器
 */
class ObjectVoltageAdapter implements IVoltage5V {
    private Voltage220V voltage220V;
    ObjectVoltageAdapter(Voltage220V voltage220V) {
        this.voltage220V = voltage220V;
    }
    @Override
    public int output5V() {
        System.out.println("----对象适配器----");
        int srcV = voltage220V.output220V();
        int dstV = srcV / 44;
        return dstV;
    }
}


