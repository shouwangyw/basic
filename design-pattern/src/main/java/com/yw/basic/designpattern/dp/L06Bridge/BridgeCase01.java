package com.yw.basic.designpattern.dp.L06Bridge;

/**
 * 桥接模式
 *
 * @author yangwei
 */
public class BridgeCase01 {
    public static void main(String[] args) {
        Phone foldedXiaoMi = new FoldedPhone(new XiaoMi());
        foldedXiaoMi.open();
        foldedXiaoMi.close();
        foldedXiaoMi.call();
        System.out.println("===============");
        Phone foldedViVo = new FoldedPhone(new ViVo());
        foldedViVo.open();
        foldedViVo.close();
        foldedViVo.call();

        System.out.println("===============");
        Phone upRightXiaomi = new UpRightPhone(new XiaoMi());
        upRightXiaomi.open();
        upRightXiaomi.close();
        upRightXiaomi.call();
    }
}

/**
 * 手机品牌：Implementor行为实现类的接口
 */
interface Brand {
    void open();
    void close();
    void call();
}

/**
 * 小米手机：行为的具体实现类
 */
class XiaoMi implements Brand {
    @Override
    public void open() {
        System.out.println("小米手机开机");
    }
    @Override
    public void close() {
        System.out.println("小米手机关机");
    }
    @Override
    public void call() {
        System.out.println("小米手机打电话");
    }
}

/**
 * ViVo手机：行为的具体实现类
 */
class ViVo implements Brand {
    @Override
    public void open() {
        System.out.println("ViVo手机开机");
    }
    @Override
    public void close() {
        System.out.println("ViVo手机关机");
    }
    @Override
    public void call() {
        System.out.println("ViVo手机打电话");
    }
}

/**
 * 手机样式：抽象类(Abstraction)
 */
abstract class Phone {
    /**
     * 组合进 品牌
     */
    private Brand brand;
    Phone(Brand brand) {
        this.brand = brand;
    }
    protected void open() {
        this.brand.open();
    }
    protected void close() {
        this.brand.close();
    }
    protected void call() {
        this.brand.call();
    }
}

/**
 * 手机样式具体的实现子类
 */
class FoldedPhone extends Phone {
    private static final String STYLE = "折叠样式";
    FoldedPhone(Brand brand) {
        super(brand);
    }
    @Override
    public void open() {
        System.out.print(STYLE);
        super.open();
    }
    @Override
    public void close() {
        System.out.print(STYLE);
        super.close();
    }
    @Override
    public void call() {
        System.out.print(STYLE);
        super.call();
    }
}

class UpRightPhone extends Phone {
    private static final String STYLE = "直立样式";
    UpRightPhone(Brand brand) {
        super(brand);
    }
    @Override
    public void open() {
        System.out.print(STYLE);
        super.open();
    }
    @Override
    public void close() {
        System.out.print(STYLE);
        super.close();
    }
    @Override
    public void call() {
        System.out.print(STYLE);
        super.call();
    }
}
