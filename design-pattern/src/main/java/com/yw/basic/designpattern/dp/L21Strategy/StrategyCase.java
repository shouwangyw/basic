package com.yw.basic.designpattern.dp.L21Strategy;

/**
 * 策略模式：鸭子问题
 *
 * @author yangwei
 */
public class StrategyCase {
    public static void main(String[] args) {
        WildDuck wildDuck = new WildDuck();
        wildDuck.fly();

        ToyDuck toyDuck = new ToyDuck();
        toyDuck.fly();
    }
}
//abstract class Duck {
//    abstract void display();
//    void quack() {
//        System.out.println("鸭子嘎嘎叫");
//    }
//    void swim() {
//        System.out.println("鸭子会游泳");
//    }
//    void fly() {
//        System.out.println("鸭子会飞翔");
//    }
//}
//class WildDuck extends Duck {
//    @Override
//    void display() {
//        System.out.println("-- 野鸭 --");
//    }
//}
//class PekingDuck extends Duck {
//    @Override
//    void display() {
//        System.out.println("-- 北京鸭 --");
//    }
//    @Override
//    void fly() {
//        System.out.println("北京鸭不能飞翔");
//    }
//}
//class ToyDuck extends Duck {
//    @Override
//    void display() {
//        System.out.println("-- 玩具鸭 --");
//    }
//    @Override
//    void quack() {
//        System.out.println("玩具鸭不会叫");
//    }
//    @Override
//    void swim() {
//        System.out.println("玩具鸭不会游泳");
//    }
//    @Override
//    void fly() {
//        System.out.println("玩具鸭不会飞");
//    }
//}
/*******************************方案改进***********************************/

/**
 * 飞翔行为：抽象策略类
 */
interface FlyBehavior {
    void fly();
}

/**
 * 下面是具体飞翔：具体策略类
 */
class GoodFlyBehavior implements FlyBehavior{
    @Override
    public void fly() {
        System.out.println("飞翔技术高超");
    }
}

class BadFlyBehavior implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("飞翔水平一般");
    }
}

class NoFlyBehavior implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("不会飞翔");
    }
}

/**
 * 叫声行为：抽象策略类
 */
interface QuackBehavor {
    void quack();
}

/**
 * 下面是具体叫声：具体策略类
 */
class GaGaQuackBehavor implements QuackBehavor {
    @Override
    public void quack() {
        System.out.println("嘎嘎叫");
    }
}

class GeGeQuackBehavor implements QuackBehavor {
    @Override
    public void quack() {
        System.out.println("咯咯叫");
    }
}

class NoQuackBehavor implements QuackBehavor {
    @Override
    public void quack() {
        System.out.println("不会叫");
    }
}

/**
 * 鸭子：环境类
 */
abstract class Duck {
    FlyBehavior flyBehavior;
    QuackBehavor quackBehavor;
    abstract void display();
    void quack() {
    }
    void fly() {
        if (flyBehavior != null) {
            flyBehavior.fly();
        }
    }
}

class WildDuck extends Duck {
    WildDuck() {
        flyBehavior = new GoodFlyBehavior();
        quackBehavor = new GeGeQuackBehavor();
    }
    @Override
    void display() {
        System.out.println("-- 野鸭 --");
    }
}

class PekingDuck extends Duck {
    PekingDuck() {
        flyBehavior = new BadFlyBehavior();
        quackBehavor = new GaGaQuackBehavor();
    }
    @Override
    void display() {
        System.out.println("-- 北京鸭子 --");
    }
}

class ToyDuck extends Duck {
    ToyDuck() {
        flyBehavior = new NoFlyBehavior();
        quackBehavor = new NoQuackBehavor();
    }
    @Override
    void display() {
        System.out.println("-- 玩具鸭子 --");
    }
}