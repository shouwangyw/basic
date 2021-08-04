package com.yw.basic.designpattern.dp2.factory_simple.factory;

import com.yw.basic.designpattern.dp2._model.ConcreteProductA;
import com.yw.basic.designpattern.dp2._model.ConcreteProductB;

/**
 * 静态方法-简单工厂
 */
public class StaticSimpleFactory {
    public static ConcreteProductA createA() {
        return new ConcreteProductA();
    }

    public static ConcreteProductB createB() {
        return new ConcreteProductB();
    }
}