package com.yw.basic.designpattern.dp2.proxy;

import com.yw.basic.designpattern.dp2.proxy.staticed.Proxy;
import com.yw.basic.designpattern.dp2.proxy.staticed.Subject;

/**
 * 测试
 */
public class TestStaticProxy {
	public static void main(String[] args) {
		Subject subject = new Proxy();
		subject.request();
	}
//	结果输出
//	before request ...
//	RealSubject request ...
//	after request ...
}