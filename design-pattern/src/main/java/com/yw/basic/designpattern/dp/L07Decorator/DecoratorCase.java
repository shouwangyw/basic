package com.yw.basic.designpattern.dp.L07Decorator;

import lombok.Data;

/**
 * 装饰者模式：星巴克咖啡订单问题
 *
 * @author yangwei
 */
public class DecoratorCase {
    public static void main(String[] args) {
        // 2份巧克力+一份牛奶的 LongBlack
        // 1、点一份 LongBlack
        Drink order = new LongBlack();
        System.out.println("费用1 = " + order.getCost() + "，描述 = " + order.getDesc());
        // 2、order加入一份牛奶
        order = new Milk(order);
        System.out.println("费用2 = " + order.getCost() + "，描述 = " + order.getDesc());
        // 3、order加入一份巧克力
        order = new Chocolate(order);
        System.out.println("费用3 = " + order.getCost() + "，描述 = " + order.getDesc());
        // 4、order再加入一份巧克力
        order = new Chocolate(order);
        System.out.println("费用4 = " + order.getCost() + "，描述 = " + order.getDesc());
    }
}

/**
 * 被装饰者：Component
 */
@Data
abstract class Drink {
    private String desc;
    private float price;
    abstract float getCost();
}

class Coffee extends Drink {
    @Override
    float getCost() {
        return super.getPrice();
    }
}

/**
 * 具体的被装饰者
 */
class Espresso extends Coffee {
    Espresso() {
        setDesc("意大利咖啡");
        setPrice(6.0f);
    }
}

class LongBlack extends Coffee {
    LongBlack() {
        setDesc("LongBlack");
        setPrice(5.0f);
    }
}

class ShortBlack extends Coffee {
    ShortBlack() {
        setDesc("ShortBlack");
        setPrice(4.0f);
    }
}

/**
 * 装饰者
 */
class Decorator extends Drink {
    private Drink drink;
    Decorator(Drink drink) {
        this.drink = drink;
    }
    @Override
    float getCost() {
        return super.getPrice() + drink.getCost();
    }
    @Override
    public String getDesc() {
        // drink.getDesc() 是被装饰者的信息
        return super.getDesc() + " " + super.getPrice() + " && " + drink.getDesc();
    }
}

/**
 * 具体的装饰者：调味品
 */
class Chocolate extends Decorator {
    Chocolate(Drink drink) {
        super(drink);
        setDesc(" 巧克力 ");
        // 调味品的价格
        setPrice(3.0f);
    }
}

class Milk extends Decorator {
    Milk(Drink drink) {
        super(drink);
        setDesc(" 牛奶 ");
        setPrice(2.0f);
    }
}

class Soy extends Decorator {
    Soy(Drink drink) {
        super(drink);
        setDesc(" 豆浆 ");
        setPrice(1.5f);
    }
}