package com.yw.basic.designpattern.principle.p06;

import java.util.ArrayList;
import java.util.List;

/**
 * 迪米特法则
 *
 * @author yangwei
 */
public class DemeterCase {
    public static void main(String[] args) {
        SchoolManager schoolManager = new SchoolManager();
        schoolManager.printAllEmployee(new CollegeManager());
    }
}
///**
// * 学校总部员工
// */
//class Employee {
//    private String id;
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getId() {
//        return id;
//    }
//}
///**
// * 学校学院员工
// */
//class CollegeEmployee {
//    private String id;
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getId() {
//        return id;
//    }
//}
///**
// * 管理学校学院员工的类
// */
//class CollegeManager {
//    public List<CollegeEmployee> getAllEmployee() {
//        List<CollegeEmployee> list = new ArrayList<CollegeEmployee>();
//        for (int i = 0; i < 10; i++) {
//            CollegeEmployee emp = new CollegeEmployee();
//            emp.setId("学校学院员工 id= " + i);
//            list.add(emp);
//        }
//        return list;
//    }
//}
///**
// * 管理学校总部员工的类
// * 分析：SchoolManager 类的直接朋友有哪些【Employee、CollegeManager】
// * 但是 CollegeEmployee 类并不是 SchoolManager 类的直接朋友，这样就违背了迪米特法则
// */
//class SchoolManager {
//    public List<Employee> getAllEmployee() {
//        List<Employee> list = new ArrayList<Employee>();
//        for (int i = 0; i < 5; i++) {
//            Employee emp = new Employee();
//            emp.setId("学校总部员工 id= " + i);
//            list.add(emp);
//        }
//        return list;
//    }
//
//    void printAllEmployee(CollegeManager sub) {
//        List<CollegeEmployee> list1 = sub.getAllEmployee();
//        System.out.println("------------学校学院员工------------");
//        for (CollegeEmployee e : list1) {
//            System.out.println(e.getId());
//        }
//        List<Employee> list2 = this.getAllEmployee();
//        System.out.println("------------学校总部员工------------");
//        for (Employee e : list2) {
//            System.out.println(e.getId());
//        }
//    }
//}

/**
 * 学校总部员工
 */
class Employee {
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

/**
 * 学校学院员工
 */
class CollegeEmployee {
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

/**
 * 管理学校学院员工的类
 */
class CollegeManager {
    public List<CollegeEmployee> getAllEmployee() {
        List<CollegeEmployee> list = new ArrayList<CollegeEmployee>();
        for (int i = 0; i < 10; i++) {
            CollegeEmployee emp = new CollegeEmployee();
            emp.setId("学校学院员工 id= " + i);
            list.add(emp);
        }
        return list;
    }

    public void printEmployee() {
        List<CollegeEmployee> list = getAllEmployee();
        System.out.println("------------学校学院员工------------");
        for (CollegeEmployee e : list) {
            System.out.println(e.getId());
        }
    }
}

/**
 * 管理学校总部员工的类
 * 分析：SchoolManager 类的直接朋友有哪些【Employee、CollegeManager】
 * 但是 CollegeEmployee 类并不是 SchoolManager 类的直接朋友，这样就违背了迪米特法则
 */
class SchoolManager {
    public List<Employee> getAllEmployee() {
        List<Employee> list = new ArrayList<Employee>();
        for (int i = 0; i < 5; i++) {
            Employee emp = new Employee();
            emp.setId("学校总部员工 id= " + i);
            list.add(emp);
        }
        return list;
    }

    void printAllEmployee(CollegeManager sub) {
//        List<CollegeEmployee> list1 = sub.getAllEmployee();
//        System.out.println("------------学校学院员工------------");
//        for (CollegeEmployee e : list1) {
//            System.out.println(e.getId());
//        }
        sub.printEmployee();
        List<Employee> list2 = this.getAllEmployee();
        System.out.println("------------学校总部员工------------");
        for (Employee e : list2) {
            System.out.println(e.getId());
        }
    }
}