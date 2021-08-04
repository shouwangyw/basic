package com.yw.basic.designpattern.dp2.factory_abstract.factory;

import com.yw.basic.designpattern.dp2.factory_abstract.product.ProductA;
import com.yw.basic.designpattern.dp2.factory_abstract.product.ProductB;

/**
 * 抽象工厂
 */
public interface AbstractFactory {
    ProductA createA();

    ProductB createB();
}