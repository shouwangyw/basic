package com.yw.basic.designpattern.principle.p02;

/**
 * 接口隔离原则
 *
 * @author yangwei
 */
public class InterfaceSegregationCase {
    interface Interface1 {
        void operation1();
    }

    interface Interface2 {
        void operation2();

        void operation3();
    }

    interface Interface3 {
        void operation4();

        void operation5();
    }

    static class B implements Interface1, Interface2 {
        @Override
        public void operation1() {
            System.out.println("B 实现了 operation1");
        }

        @Override
        public void operation2() {
            System.out.println("B 实现了 operation2");
        }

        @Override
        public void operation3() {
            System.out.println("B 实现了 operation3");
        }
    }

    static class D implements Interface1, Interface3 {
        @Override
        public void operation1() {
            System.out.println("D 实现了 operation1");
        }

        @Override
        public void operation4() {
            System.out.println("D 实现了 operation4");
        }

        @Override
        public void operation5() {
            System.out.println("D 实现了 operation5");
        }
    }

    static class A {
        void depend1(Interface1 i) {
            i.operation1();
        }

        void depend2(Interface2 i) {
            i.operation2();
        }

        void depend3(Interface2 i) {
            i.operation3();
        }
    }

    static class C {
        void depend1(Interface1 i) {
            i.operation1();
        }

        void depend4(Interface3 i) {
            i.operation4();
        }

        void depend5(Interface3 i) {
            i.operation5();
        }
    }

    public static void main(String[] args) {
        A a = new A();
        // A 类通过接口去依赖 B 类
        a.depend1(new B());
        a.depend2(new B());
        a.depend3(new B());

        C c = new C();
        // C 类通过接口去依赖 D 类
        c.depend1(new D());
        c.depend4(new D());
        c.depend5(new D());
    }
}
