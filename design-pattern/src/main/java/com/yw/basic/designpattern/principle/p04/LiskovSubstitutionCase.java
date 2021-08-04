package com.yw.basic.designpattern.principle.p04;

/**
 * 里氏替换原则
 *
 * @author yangwei
 */
public class LiskovSubstitutionCase {
    public static void main(String[] args) {
        A a = new A();
        System.out.println("11-3=" + a.func1(11, 3));
        System.out.println("1-8=" + a.func1(1, 8));
        System.out.println("-----------------------");
        B b = new B();
//        System.out.println("11-3=" + b.func1(11, 3));
//        System.out.println("1-8=" + b.func1(1, 8));
//        System.out.println("11+3+9=" + b.func2(11, 3));
        // 因为B不在集成A类，因此调用者不会再用func1来求减法，而是用func3
        System.out.println("11-3=" + b.func3(11, 3));
        System.out.println("1-8=" + b.func3(1, 8));
        System.out.println("11+3+9=" + b.func2(11, 3));

    }
}
//class A {
//    public int func1(int num1, int num2) { return num1 - num2; }
//}
//class B extends A {
//    @Override
//    public int func1(int a, int b) { return a + b; }
//    public int func2(int a, int b) { return func1(a, b) + 9; }
//}
////结果输出
////11-3=8
////1-8=-7
////-----------------------
////11-3=14
////1-8=9
////11+3+9=23

// 改进方案如下：
class Base {

}

class A extends Base {
    public int func1(int num1, int num2) {
        return num1 - num2;
    }
}

class B extends Base {
    // 如果B需要使用A类的方法，使用组合关系
    private A a = new A();

    public int func1(int a, int b) {
        return a + b;
    }

    public int func2(int a, int b) {
        return func1(a, b) + 9;
    }

    public int func3(int a, int b) {
        return this.a.func1(a, b);
    }
}