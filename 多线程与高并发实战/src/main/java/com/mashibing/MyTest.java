package com.mashibing;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class MyTest {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 消1：生1
         * 消2：生2
         * 消3：生3
         */
        SynchronousQueue queue = new SynchronousQueue(true);
        /**
         * 消1：生3
         * 消2：生2
         * 消3：生1
         */
//        SynchronousQueue queue = new SynchronousQueue(false);

        new Thread(() -> {
            try {
                queue.put("生1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                queue.put("生2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                queue.put("生3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(100);
        new Thread(() -> System.out.println("消1：" + queue.poll())).start();
        Thread.sleep(100);
        new Thread(() -> System.out.println("消2：" + queue.poll())).start();
        Thread.sleep(100);
        new Thread(() -> System.out.println("消3：" + queue.poll())).start();
    }
}