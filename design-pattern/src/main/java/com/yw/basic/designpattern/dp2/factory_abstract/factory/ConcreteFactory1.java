package com.yw.basic.designpattern.dp2.factory_abstract.factory;

import com.yw.basic.designpattern.dp2.factory_abstract.product.ConcreteProductA1;
import com.yw.basic.designpattern.dp2.factory_abstract.product.ConcreteProductB1;
import com.yw.basic.designpattern.dp2.factory_abstract.product.ProductA;
import com.yw.basic.designpattern.dp2.factory_abstract.product.ProductB;

/**
 * 具体工厂1
 */
public class ConcreteFactory1 implements AbstractFactory {
    public ProductA createA() {
        return new ConcreteProductA1();
    }

    public ProductB createB() {
        return new ConcreteProductB1();
    }
}