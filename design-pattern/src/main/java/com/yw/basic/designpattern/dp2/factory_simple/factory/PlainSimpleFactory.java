package com.yw.basic.designpattern.dp2.factory_simple.factory;

import com.yw.basic.designpattern.dp2._model.ConcreteProductA;
import com.yw.basic.designpattern.dp2._model.ConcreteProductB;
import com.yw.basic.designpattern.dp2._model.Product;
import com.yw.basic.designpattern.dp2.factory_simple.ProductTypes;

/**
 * 普通-简单工厂
 */
public class PlainSimpleFactory {
    public static Product create(String type) {
        Product product = null;
        if (type.equalsIgnoreCase(ProductTypes.TYPE_A)) {
            product = new ConcreteProductA();
        } else if (type.equalsIgnoreCase(ProductTypes.TYPE_B)) {
            product = new ConcreteProductB();
        }
        return product;
    }
}