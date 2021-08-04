package com.yw.basic.designpattern.dp2.factory_abstract.factory;

import com.yw.basic.designpattern.dp2.factory_abstract.product.ConcreteProductA2;
import com.yw.basic.designpattern.dp2.factory_abstract.product.ConcreteProductB2;
import com.yw.basic.designpattern.dp2.factory_abstract.product.ProductA;
import com.yw.basic.designpattern.dp2.factory_abstract.product.ProductB;

/**
 * 具体工厂2
 */
public class ConcreteFactory2 implements AbstractFactory {
    public ProductA createA() {
        return new ConcreteProductA2();
    }

    public ProductB createB() {
        return new ConcreteProductB2();
    }
}