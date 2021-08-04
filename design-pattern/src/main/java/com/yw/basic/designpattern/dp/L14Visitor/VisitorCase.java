package com.yw.basic.designpattern.dp.L14Visitor;

import java.util.LinkedList;
import java.util.List;

/**
 * 访问者模式：测评系统的需求
 *
 * @author yangwei
 */
public class VisitorCase {
    public static void main(String[] args) {
        // 创建数据结构
        ObjectStructure structure = new ObjectStructure();
        structure.attach(new Male("Tom"));
        structure.attach(new Male("Jack"));
        structure.attach(new Male("Smith"));
        structure.attach(new Female("Lily"));
        structure.attach(new Female("Sari"));

        // 成功
        Succcess succcess = new Succcess();
        structure.display(succcess);
        // 失败
        Fail fail = new Fail();
        structure.display(fail);
    }
}

/**
 * 抽象访问者：测评的动作
 */
abstract class Action {
    abstract void getMaleResult(Male male);
    abstract void getFemaleResult(Female female);
}

/**
 * 具体访问者
 */
class Succcess extends Action {
    @Override
    void getMaleResult(Male male) {
        System.out.println(male.name + ": Yes");
    }
    @Override
    void getFemaleResult(Female female) {
        System.out.println(female.name + ": Yes");
    }
}

class Fail extends Action {
    @Override
    void getMaleResult(Male male) {
        System.out.println(male.name + ": No");
    }
    @Override
    void getFemaleResult(Female female) {
        System.out.println(female.name + ": No");
    }
}

/**
 * 抽象元素：人
 */
abstract class Person {
    public String name;
    Person(String name) {
        this.name = name;
    }
    abstract void accept(Action action);
}

/**
 * 具体的元素：男人和女人
 * 合理我们使用到了双分派，即首先在客户端程序中，将具体状态作为参数传递到了【Male、Female】中
 * 然后【Male、Female】类调用作为参数的"具体方法"中getXxxResult，同时将自己（this）作为参数传入，即第二次分派
 */
class Male extends Person {
    Male(String name) {
        super("[M]" + name);
    }
    @Override
    void accept(Action action) {
        action.getMaleResult(this);
    }
}

class Female extends Person {
    Female(String name) {
        super("[F]" + name);
    }
    @Override
    void accept(Action action) {
        action.getFemaleResult(this);
    }
}

/**
 * 数据结构：管理了很多人(Male、Female)
 */
class ObjectStructure {
    /**
     * 维护了一个集合
     */
    private List<Person> persons = new LinkedList<>();
    /**
     * 增加
     */
    public void attach(Person p) {
        persons.add(p);
    }
    /**
     * 移除
     */
    public void detach(Person p) {
        persons.remove(p);
    }
    /**
     * 显示
     */
    public void display(Action action) {
        for (Person p : persons) {
            p.accept(action);
        }
    }
}
