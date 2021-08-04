package com.yw.basic.designpattern.dp.L17Mediator;

import java.util.HashMap;
import java.util.Map;

/**
 * 中介者模式：智能家庭管理
 * @author yangwei
 */
public class MediatorCase {
    public static void main(String[] args) {
        // 创建一个中介者对象
        Mediator mediator = new ConcreteMediator();
        // 创建一个 Alarm 并加入到 ConcreteMediator 的Map中
        Alarm alarm = new Alarm(mediator, "alarm");
        // 创建一个 CoffeeMachine 并加入到 ConcreteMediator 的Map中
        CoffeeMachine coffeeMachine = new CoffeeMachine(mediator, "coffeeMachine");
        // 创建一个 Curtains 并加入到 ConcreteMediator 的Map中
        Curtains curtains = new Curtains(mediator, "curtains");
        TV tv = new TV(mediator, "tv");
        // 让闹钟发出消息
        alarm.sendMessage(0);
        coffeeMachine.finishCoffee();
        alarm.sendMessage(1);
    }
}

/**
 * 中介者：抽象类
 */
abstract class Mediator {
    /**
     * 将一个同事类加入到集合
     */
    abstract void register(String colleagueName, Colleague colleague);
    /**
     * 接收消息，具体的同事对象发出
     */
    abstract void getMessage(int stateChange, String colleagueName);
    abstract void sendMessage();
}

/**
 * 具体的中介者类
 */
class ConcreteMediator extends Mediator {
    /**
     * 集合：放入了所有的同事类
     */
    private Map<String, Colleague> colleagueMap;
    private Map<String, String> interMap;
    ConcreteMediator() {
        colleagueMap = new HashMap<>();
        interMap = new HashMap<>();
    }
    @Override
    void register(String colleagueName, Colleague colleague) {
        colleagueMap.put(colleagueName, colleague);
        if (colleague instanceof Alarm) {
            interMap.put("Alarm", colleagueName);
        } else if (colleague instanceof CoffeeMachine) {
            interMap.put("CoffeeMachine", colleagueName);
        } else if (colleague instanceof TV) {
            interMap.put("TV", colleagueName);
        } else if (colleague instanceof Curtains) {
            interMap.put("Curtains", colleagueName);
        }
    }
    @Override
    void getMessage(int stateChange, String colleagueName) {
        Colleague colleague = colleagueMap.get(colleagueName);
        if (colleague instanceof Alarm) {
            if (stateChange == 0) {
                ((CoffeeMachine) colleagueMap.get(interMap.get("CoffeeMachine"))).startCoffee();
                ((TV) colleagueMap.get(interMap.get("TV"))).startTV();
            } else if (stateChange == 1) {
                ((TV) colleagueMap.get(interMap.get("TV"))).stopTV();
            }
        } else if (colleague instanceof CoffeeMachine) {
            ((Curtains) colleagueMap.get(interMap.get("Curtains"))).upCurtains();
        } else if (colleague instanceof TV) {
            // ...
        } else if (colleague instanceof Curtains) {
            // ...
        }
    }
    @Override
    void sendMessage() {

    }
}

/**
 * 同事：抽象类
 */
abstract class Colleague {
    private Mediator mediator;
    protected String name;
    Colleague(Mediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }
    public Mediator getMediator() {
        return mediator;
    }
    abstract void sendMessage(int stateChange);
}

/**
 * 具体的同事类
 */
class Alarm extends Colleague {
    Alarm(Mediator mediator, String name) {
        super(mediator, name);
        // 在创建Alarm对象时，将自己放入到ConcreteMediator对象中
        mediator.register(name, this);
    }
    @Override
    void sendMessage(int stateChange) {
        // 调用中介者对象的 getMessage
        this.getMediator().getMessage(stateChange, this.name);
    }
    public void sendAlarm(int stateChange) {
        this.sendMessage(stateChange);
    }
}

class CoffeeMachine extends Colleague {
    CoffeeMachine(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }
    @Override
    void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.name);
    }
    public void startCoffee() {
        System.out.println("It's time to start coffee!");
    }
    public void finishCoffee() {
        System.out.println("After 5 minutes!");
        System.out.println("Coffee is OK!");
        sendMessage(0);
    }
}

class TV extends Colleague {
    TV(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }
    @Override
    void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.name);
    }
    public void startTV() {
        System.out.println("It's time to Start TV!");
    }
    public void stopTV() {
        System.out.println("Stop TV!");
    }
}

class Curtains extends Colleague {
    Curtains(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }
    @Override
    void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.name);
    }
    public void upCurtains() {
        System.out.println("I am holding Up Curtains!");
    }
}
