package com.yw.basic.designpattern.dp2.builder;

import com.yw.basic.designpattern.dp2.builder.product.Product;

/**
 * 具体构建者
 */
public class ConcreteBuilder implements Builder {
    private Product product = new Product();

    public Builder buildPartA() {
        product.setPartA("A");
        return this;
    }

    public Builder buildPartB() {
        product.setPartB("B");
        return this;
    }

    public Builder buildPartC() {
        product.setPartC("C");
        return this;
    }

    public Product build() {
        return product;
    }
}