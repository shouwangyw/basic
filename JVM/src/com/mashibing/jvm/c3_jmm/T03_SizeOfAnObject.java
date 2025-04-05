package com.mashibing.jvm.c3_jmm;

import com.mashibing.jvm.agent.ObjectSizeAgent;

public class T03_SizeOfAnObject {
    public static void main(String[] args) {
        System.out.println(ObjectSizeAgent.sizeOf(new Object())); // 16
        System.out.println(ObjectSizeAgent.sizeOf(new int[] {})); // 默认：16（开启了压缩），不压缩 24
        System.out.println(ObjectSizeAgent.sizeOf(new P()));
    }

    //一个Object占多少个字节
    // -XX:+UseCompressedClassPointers -XX:+UseCompressedOops
    // Oops = ordinary object pointers
    private static class P {
                        //8 _markword
                        //4 _class pointer
        int id;         //4
        String name;    //4
        int age;        //4

        byte b1;        //1
        byte b2;        //1

        Object o;       //4
        byte b3;        //1

    }
}
