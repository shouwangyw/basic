package com.mashibing.cache;

import com.mashibing.entity.Air;
import com.mashibing.service.AirService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zjw
 * @description
 */
@Repository
public class AirCache implements InitializingBean {

    // 一个只有一个工作线程，并且可以执行的定时任务的线程池。
    private static final ScheduledExecutorService AIR_TASK_POOL = Executors.newSingleThreadScheduledExecutor();

    // 每个线程查询的数据条数
    private static final long AIR_COUNT = 5000;

    // 摸鱼的方式构建一个线程
    private static final ExecutorService AIR_FIND_POOL = Executors.newFixedThreadPool(10);

    // JVM缓存   写读
    private static final ConcurrentHashMap<Long, Air> AIR_CACHE = new ConcurrentHashMap<Long, Air>(128);

    // 做全量更新时，上好写锁，不允许读取数据。  读取数据时，需要走读锁
    private static final ReentrantReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock.WriteLock WRITE_LOCK = READ_WRITE_LOCK.writeLock();
    private static final ReentrantReadWriteLock.ReadLock READ_LOCK = READ_WRITE_LOCK.readLock();

    // 查询数据库数据
    @Autowired
    private AirService airService;

    @Override
    public void afterPropertiesSet() throws Exception {
        // scheduleAtFixedRate     每次间隔3s，但是任务的执行时间，也会算到这3s内。
        // scheduleWithFixedDelay  每次间隔3s，等待上个任务完成后，间隔3s再执行下次任务。
        AIR_TASK_POOL.scheduleWithFixedDelay(() -> {
            // 这里做全量更新
            // 1、查询数据库中的全部数据
            // 查询数据的总条数
            long count = airService.findCount();
            long taskCount = (count % AIR_COUNT) == 0 ? count / AIR_COUNT : count / AIR_COUNT + 1;
            List<Future<List<Air>>> taskList = new ArrayList<>();
            for (int i = 0; i < taskCount; i++) {
                long offset = i * AIR_COUNT;
                Future<List<Air>> airListFuture = AIR_FIND_POOL.submit(() -> {
                    List<Air> list = airService.findLimit(offset, AIR_COUNT);
                    return list;
                });
                taskList.add(airListFuture);
            }
            // 根据数据条数做好每个线程查询的数据
            // 每个线程都查询完毕后，将数据整合到map中。
            ConcurrentHashMap<Long, Air> allData = new ConcurrentHashMap<>();
            CountDownLatch countDownLatch = new CountDownLatch(taskList.size());
            for (Future future : taskList) {
                try {
                    // 这里获取线程池任务的结果，结果到手后，才会停止阻塞。
                    List<Air> airList = (List<Air>) future.get();
                    AIR_FIND_POOL.execute(() -> {
                        for (Air air : airList) {
                            allData.put(air.getId(), air);
                        }
                        countDownLatch.countDown();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 2、等到，前面的put操作的线程全部ok，这里再同步到JVM缓存中。
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writeAll(allData);
        }, 0, 3, TimeUnit.SECONDS);
    }

    // 全量更新   （数据体量比较小，咱们采用全量更新。  如果数据体量比较大，采用增量更新，增量更新，需要考虑多个JVM之间的数据同步，MQ的广播机制去玩）
    private void writeAll(Map<Long, Air> allData) {
        // 获取写锁
        WRITE_LOCK.lock();
        try {
            // 清除缓存全部数据
            AIR_CACHE.clear();
            // 全量更新
            AIR_CACHE.putAll(allData);
        } finally {
            WRITE_LOCK.unlock();
        }
    }

    // 对外提供查询功能
    public Air getAirById(Long id) {
        if (id == null) return null;
        boolean b = READ_LOCK.tryLock();
        if (!b) {
            return null;
        }
        try {
            Air air = AIR_CACHE.get(id);
            return air;
        } catch (Exception e) {
            return null;
        } finally {
            READ_LOCK.unlock();
        }
    }

}
