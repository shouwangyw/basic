package com.yw.basic.designpattern.dp.L15Iterator;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 迭代器模式：院校遍历
 *
 * @author yangwei
 */
public class IteratorCase {
    public static void main(String[] args) {
        ComputerCollege computerCollege = new ComputerCollege();
        computerCollege.addDepartment("Java专业", "学习Java");
        computerCollege.addDepartment("Php专业", "学习Php");
        computerCollege.addDepartment("大数据专业", "学习大数据");

        InfoCollege infoCollege = new InfoCollege();
        infoCollege.addDepartment("信息安全专业", "学习信息安全");
        infoCollege.addDepartment("网络安全专业", "学习网络安全");

        List<College> colleges = new ArrayList<>();
        colleges.add(computerCollege);
        colleges.add(infoCollege);

        OutputImpl output = new OutputImpl(colleges);
        output.printCollege();
    }
}

/**
 * 系
 */
@Data
@AllArgsConstructor
class Department {
    private String name;
    private String desc;
}

/**
 * 计算机学院迭代器
 */
class ComputerCollegeIterator implements Iterator<Department> {
    /**
     * 这里我们定义 Department 是以数组形式存放
     */
    private Department[] departments;
    /**
     * 遍历的位置
     */
    private int position;
    ComputerCollegeIterator(Department[] departments) {
        this.departments = departments;
    }
    @Override
    public boolean hasNext() {
        return position < departments.length && departments[position] != null;
    }
    @Override
    public Department next() {
        Department department = departments[position];
        position += 1;
        return department;
    }
    @Override
    public void remove() {
    }
}

/**
 * 信息工程学院迭代器
 */
class InfoCollegeIterator implements Iterator<Department> {
    /**
     * 这里我们定义 Department 是以List集合形式存放
     */
    private List<Department> departments;
    /**
     * 遍历的索引位置
     */
    private int index = -1;
    InfoCollegeIterator(List<Department> departments) {
        this.departments = departments;
    }
    @Override
    public boolean hasNext() {
        if (index >= departments.size() - 1) {
            return false;
        } else {
            index += 1;
            return true;
        }
    }
    @Override
    public Department next() {
        return departments.get(index);
    }
    @Override
    public void remove() {
    }
}

/**
 * 聚合接口：学院
 */
interface College {
    String getName();
    void addDepartment(String name, String desc);
    Iterator<Department> createIterator();
}

/**
 * 计算机学院
 */
class ComputerCollege implements College {
    private Department[] departments;
    /**
     * 保存当前数组对象的个数
     */
    private int numOfDep;
    ComputerCollege() {
        departments = new Department[5];
    }
    @Override
    public String getName() {
        return "计算机学院";
    }
    @Override
    public void addDepartment(String name, String desc) {
        departments[numOfDep++] = new Department(name, desc);
    }
    @Override
    public Iterator<Department> createIterator() {
        return new ComputerCollegeIterator(departments);
    }
}

/**
 * 信息工程学院
 */
class InfoCollege implements College {
    private List<Department> departments;
    InfoCollege() {
        departments = new ArrayList<>();
    }
    @Override
    public String getName() {
        return "信息工程学院";
    }
    @Override
    public void addDepartment(String name, String desc) {
        departments.add(new Department(name, desc));
    }
    @Override
    public Iterator<Department> createIterator() {
        return new InfoCollegeIterator(departments);
    }
}

/**
 * 输出
 */
class OutputImpl {
    /**
     * 学院集合
     */
    private List<College> colleges;
    OutputImpl(List<College> colleges) {
        this.colleges = colleges;
    }
    /**
     * 遍历所有学院，调用 printDepartment
     */
    public void printCollege() {
        Iterator<College> iterator = colleges.iterator();
        while (iterator.hasNext()) {
            College college = iterator.next();
            System.out.println("----" + college.getName() + "----");
            printDepartment(college.createIterator());
        }
    }
    private void printDepartment(Iterator<Department> iterator) {
        while (iterator.hasNext()) {
            Department department = iterator.next();
            System.out.println(department.getName());
        }
    }
}