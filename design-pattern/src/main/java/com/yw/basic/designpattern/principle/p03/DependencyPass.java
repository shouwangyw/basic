package com.yw.basic.designpattern.principle.p03;

/**
 * @author yangwei
 */
public class DependencyPass {
    public static void main(String[] args) {
//        // 通过接口传递实现依赖
//        OpenAndClose openAndClose = new OpenAndClose();
//        openAndClose.open(new ChangHong());

//        // 通过构造器进行依赖传递
//        OpenAndClose2 openAndClose = new OpenAndClose2(new ChangHong());
//        openAndClose.open();

        // 通过 setter 方法进行依赖传递
        OpenAndClose3 openAndClose = new OpenAndClose3();
        openAndClose.setTv(new ChangHong());
        openAndClose.open();
    }
}

/**
 * 电视接口
 */
interface ITV {
    void play();
}

/**
 * 开关接口
 */
interface IOpenAndClose {
    void open(ITV tv);
}

/**
 * 方式 1: 通过接口传递实现依赖
 */
class ChangHong implements ITV {
    @Override
    public void play() {
        System.out.println("长虹电视机，打开");
    }
}

class OpenAndClose implements IOpenAndClose {
    @Override
    public void open(ITV tv) {
        tv.play();
    }
}

/**
 * 方式 2: 通过构造方法依赖传递
 */
interface IOpenAndClose2 {
    void open();
}

class OpenAndClose2 implements IOpenAndClose2 {
    private ITV tv;

    public OpenAndClose2(ITV tv) {
        this.tv = tv;
    }

    @Override
    public void open() {
        this.tv.play();
    }
}

/**
 * 方式 3: 通过 setter 方法传递
 */
interface IOpenAndClose3 {
    void open();

    void setTv(ITV tv);
}

class OpenAndClose3 implements IOpenAndClose3 {
    private ITV tv;

    @Override
    public void open() {
        this.tv.play();
    }

    @Override
    public void setTv(ITV tv) {
        this.tv = tv;
    }
}