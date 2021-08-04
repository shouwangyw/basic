package com.yw.basic.designpattern.dp2.factory_method.factory;

import com.yw.basic.designpattern.dp2._model.ConcreteProductB;
import com.yw.basic.designpattern.dp2._model.Product;

/**
 * 具体的方法工厂B
 */
public class ConcreteFactoryB implements MethodFactory {
    public Product create() {
        return new ConcreteProductB();
    }
}