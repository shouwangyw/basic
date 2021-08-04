package com.yw.basic.designpattern.dp.L18Memento;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 备忘录模式:游戏角色状态恢复问题
 *
 * @author yangwei
 */
public class MementoCase {
    public static void main(String[] args) {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();
        originator.setState("状态1 攻击力100");
        // 保存当前状态
        caretaker.add(originator.saveStateMemento());
        System.out.println("当前的状态是：" + originator.getState());

        originator.setState("状态2 攻击力80");
        caretaker.add(originator.saveStateMemento());
        System.out.println("当前的状态是：" + originator.getState());

        originator.setState("状态3 攻击力30");
        caretaker.add(originator.saveStateMemento());

        System.out.println("当前的状态是：" + originator.getState());
        // 状态恢复
        originator.recoveryState(caretaker.get(1));
        System.out.println("恢复到状态2：" + originator.getState());
    }
}

@Data
class Originator {
    private String state;
    /**
     * 编写一个方法，可以保存一个状态对象Memento
     */
    public Memento saveStateMemento() {
        return new Memento(state);
    }
    /**
     * 通过备忘录对象，恢复状态
     */
    public void recoveryState(Memento memento) {
        state = memento.getState();
    }
}

class Memento {
    private String state;
    Memento(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }
}

class Caretaker {
    /**
     * 在List集合中有很多备忘录对象
     */
    private List<Memento> mementos = new ArrayList<>();
    public void add(Memento memento) {
        mementos.add(memento);
    }
    public Memento get(int index) {
        return mementos.get(index);
    }
}