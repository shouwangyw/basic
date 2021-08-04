package com.yw.basic.designpattern.dp2.adapter;

/**
 * 测试
 */
public class Test {
    public static void main(String[] args) {
        Target target1 = new ClassAdapter();
        target1.method1(); // This is Adaptee's method
        target1.method2(); // This is Target's method

        Adaptee adaptee = new Adaptee();
        Target target2 = new ObjectAdapter(adaptee);
        target2.method1(); // This is Adaptee's method
        target2.method2(); // This is Target's method

        Target targetA = new TargetA();
        Target targetB = new TargetB();

        targetA.method1();
        targetA.method2();
        targetB.method1();
        targetB.method2();
//		输出结果：
//		This is TargetA's method1
//		InterfaceAdapter has implement Target's method2
//		InterfaceAdapter has implement Target's method1
//		This is TargetB's method2
    }
}