package com.yw.basic.designpattern.dp2.factory_abstract;

import com.yw.basic.designpattern.dp2.factory_abstract.factory.AbstractFactory;
import com.yw.basic.designpattern.dp2.factory_abstract.factory.ConcreteFactory1;
import com.yw.basic.designpattern.dp2.factory_abstract.factory.ConcreteFactory2;
import com.yw.basic.designpattern.dp2.factory_abstract.product.ProductA;
import com.yw.basic.designpattern.dp2.factory_abstract.product.ProductB;

/**
 * 测试
 */
public class Test {
    public static void main(String[] args) {
        AbstractFactory factory1 = new ConcreteFactory1();
        ProductA productA = factory1.createA();
        productA.methodDiff();
        ProductB productB = factory1.createB();
        productB.methodDiff();

        AbstractFactory factory2 = new ConcreteFactory2();
        productA = factory2.createA();
        productA.methodDiff();
        productB = factory2.createB();
        productB.methodDiff();
    }
}