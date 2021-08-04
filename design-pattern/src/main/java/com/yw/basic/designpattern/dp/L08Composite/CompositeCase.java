package com.yw.basic.designpattern.dp.L08Composite;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式：学校院系展示需求
 *
 * @author yangwei
 */
public class CompositeCase {
    public static void main(String[] args) {
        // 从大到小创建对象
        University university = new University("清华大学");
        College college1 = new College("计算机学院");
        College college2 = new College("信息工程学院");

        college1.add(new Department("软件工程"));
        college1.add(new Department("网络安全"));
        college1.add(new Department("计算机科学与技术"));
        college2.add(new Department("通信工程"));
        college2.add(new Department("信息工程"));

//        college1.print();

        university.add(college1);
        university.add(college2);

        university.print();
    }
}

@Data
abstract class OrgComponent {
    private String name;
    OrgComponent(String name){
        this.name = name;
    }
    protected void add(OrgComponent component) {
        // 默认实现
        throw new UnsupportedOperationException();
    }
    protected void remove(OrgComponent component) {
        // 默认实现
        throw new UnsupportedOperationException();
    }
    protected void get(int index) {
        // 默认实现
        throw new UnsupportedOperationException();
    }
    abstract void print();
}

class University extends OrgComponent {
    List<OrgComponent> components = new ArrayList<>();
    University(String name) {
        super(name);
    }
    @Override
    public void add(OrgComponent component) {
        components.add(component);
    }
    @Override
    public void remove(OrgComponent component) {
        components.remove(component);
    }
    @Override
    public void get(int index) {
        components.get(index);
    }
    @Override
    public void print() {
        System.out.println("--------" + getName() +"--------");
        if (components.size() > 0) {
            for (OrgComponent component : components) {
                component.print();
            }
        }
    }
}

class College extends OrgComponent {
    List<OrgComponent> components = new ArrayList<>();
    College(String name) {
        super(name);
    }
    @Override
    public void add(OrgComponent component) {
        // 实际业务中，add方法不一定完全一样
        components.add(component);
    }
    @Override
    public void remove(OrgComponent component) {
        components.remove(component);
    }
    @Override
    public void get(int index) {
        components.get(index);
    }
    @Override
    public void print() {
        System.out.println("--------" + getName() +"--------");
        if (components.size() > 0) {
            for (OrgComponent component : components) {
                component.print();
            }
        }
    }
}

/**
 * 叶子结点
 */
class Department extends OrgComponent {
    Department(String name) {
        super(name);
    }
    @Override
    public void print() {
        System.out.println(getName());
    }
}
