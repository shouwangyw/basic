package com.mashibing;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 */
public class Task implements Delayed {
    /** 任务名称 */
    private String name;
    /** 什么时间点执行 */
    private long time;

    public Task(String name, long delay) {
        this.name = name;
        this.time = System.currentTimeMillis() + delay;
    }

    /**
     * 设置什么时间可以出延迟队列
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(time - System.currentTimeMillis(), TimeUnit.MICROSECONDS);
    }

    /**
     * 两个任务在插入到延迟队列的比较方式
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.time - ((Task) o).getTime());
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", time=" + time +
                '}';
    }
}
