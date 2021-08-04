package com.yw.basic.designpattern.dp2.adapter;

/**
 * 接口适配抽象类
 */
public abstract class InterfaceAdapter implements Target{
	public void method1(){
		System.out.println("InterfaceAdapter has implement Target's method1");
	}
	public void method2(){
		System.out.println("InterfaceAdapter has implement Target's method2");
	}
}