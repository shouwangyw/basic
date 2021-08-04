package com.yw.basic.designpattern.dp2.factory_method.factory;

import com.yw.basic.designpattern.dp2._model.ConcreteProductA;
import com.yw.basic.designpattern.dp2._model.Product;

/**
 * 具体的方法工厂A
 */
public class ConcreteFactoryA implements MethodFactory {
	public Product create() {
		return new ConcreteProductA();
	}
}