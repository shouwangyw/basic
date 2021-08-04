package com.yw.basic.designpattern.principle.p03;

/**
 * 依赖倒转原则
 * 完成 Person 接收消息的功能
 *
 * @author yangwei
 */
public class DependenceInversionCase {
    //region -- 方式一
//    /**
//     * 方式一
//     * 优点：简单
//     * 缺点：如果我们通过 微信、短信 等获取，则需要新增类，同时Person类也需要增加相应的接收方法
//     */
//    static class Email {
//        String getInfo() {
//            return "电子邮件信息：Hello World";
//        }
//    }
//    static class Person {
//        void receive(Email email) {
//            System.out.println(email.getInfo());
//        }
//    }
    //endregion

    //region
    /**
     * 方式二
     * 引入一个抽象的接口 IReceiver, 表示接收者, 这样 Person 类与接口 IReceiver 发生依赖
     * 因为 Email, WeiXin 等等属于接收的范围，他们各自实现 IReceiver 接口就可以了，这样我们就符号依赖倒转原则
     */
    interface IReceiver {
        String getInfo();
    }

    static class Email implements IReceiver {
        @Override
        public String getInfo() {
            return "电子邮件信息：Hello World";
        }
    }

    static class WeChat implements IReceiver {
        @Override
        public String getInfo() {
            return "微信信息：来红包啦！";
        }
    }

    static class Person {
        // 这里是对接口的依赖
        void receive(IReceiver receiver) {
            System.out.println(receiver.getInfo());
        }
    }
    //endregion

    public static void main(String[] args) {
        Person person = new Person();
        person.receive(new Email());
        person.receive(new WeChat());
    }
}
