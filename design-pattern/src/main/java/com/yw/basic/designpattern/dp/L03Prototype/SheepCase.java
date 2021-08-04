package com.yw.basic.designpattern.dp.L03Prototype;

/**
 * 原型模式
 *
 * @author yangwei
 */
public class SheepCase {
    public static void main(String[] args) {
        // 传统方法
        Sheep sheep = new Sheep("Tom", 1, "白色");
//        Sheep sheep2 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
//        Sheep sheep3 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
//        Sheep sheep4 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
//        Sheep sheep5 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
//        // ...

        Sheep sheep2 = sheep.getCopy();
        Sheep sheep3 = sheep.getCopy();
        Sheep sheep4 = sheep.getCopy();
        Sheep sheep5 = sheep.getCopy();

        System.out.println(sheep);
        System.out.println(sheep2);
        System.out.println(sheep3);
        System.out.println(sheep4);
        System.out.println(sheep5);
    }
}
//class Sheep {
//    private String name;
//    private int age;
//    private String color;
//    Sheep(String name, int age, String color) {
//        this.name = name;
//        this.age = age;
//        this.color = color;
//    }
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//    public int getAge() {
//        return age;
//    }
//    public void setAge(int age) {
//        this.age = age;
//    }
//    public String getColor() {
//        return color;
//    }
//    public void setColor(String color) {
//        this.color = color;
//    }
//}

/**
 * 代码改进
 */
class Sheep implements Cloneable {
    private String name;
    private int age;
    private String color;

    Sheep(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Sheep: {name: " + name + ", age: " + age + ", color: " + color + "}";
    }

    @Override
    protected Object clone() {
        try {
            Sheep sheep = (Sheep) super.clone();
            return super.clone();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Sheep getCopy() {
        return (Sheep) clone();
    }
}
