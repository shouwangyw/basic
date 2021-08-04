package com.yw.basic.designpattern.dp2.builder;

import com.yw.basic.designpattern.dp2.builder.product.Product;

/**
 * 导演类
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    // 产品构建与组装方法
    public Product constructProduct() {
        return builder.buildPartA().buildPartB().buildPartC().build();
    }
}