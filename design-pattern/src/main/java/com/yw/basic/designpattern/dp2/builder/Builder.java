package com.yw.basic.designpattern.dp2.builder;

import com.yw.basic.designpattern.dp2.builder.product.Product;

/**
 * 构建者接口
 */
public interface Builder {
	Builder buildPartA();
	Builder buildPartB();
	Builder buildPartC();
	Product build();
}