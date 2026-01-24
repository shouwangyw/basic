package com.mashibing.service.impl;

import com.mashibing.entity.Air;
import com.mashibing.mapper.AirMapper;
import com.mashibing.service.PushService;
import com.mashibing.util.PushUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zjw
 * @description
 */
@Service
@Slf4j
public class PushServiceImpl implements PushService {

    private final int COUNT = 10000;
    private final int COUNT_ID = 9999;

    private final ExecutorService executor = Executors.newFixedThreadPool(50);


    @Autowired
    private PushUtil pushUtil;

    @Resource
    private AirMapper airMapper;


    /**
     * 多线程推送数据到审核服务
     */
    @Override
    public void pushData(String date) throws ExecutionException, InterruptedException {

        // 将数据计算做好。
        //1.1 获取到今天需要推送数据的总数
        long totalCount = airMapper.selectCountNeedPush(date);
        System.out.println(totalCount);
        //1.2 查询今日需要推送的数据的第一个id
        Long firstId = airMapper.selectOneNeedPush(date);
        //1.3 计算投递任务的个数
        long times = totalCount % COUNT == 0 ? totalCount / COUNT : totalCount / COUNT + 1;

        List<Future<String>> futureList = new ArrayList<>();
        //1.4 开始计算每个线程推送的数据
        for (int i = 0; i < times; i++) {
            // 计算好每次每个线程需要查询的数据范围
            long fromId = firstId;
            long toId = firstId + COUNT_ID;
            firstId = firstId + COUNT;
            //2.1 基于线程池投递数据
            Future<String> future = executor.submit(() -> {
                //2.2 查询当前线程需要推送的数据
                List<Air> airList = airMapper.selectByIdAndLimit(date, fromId, toId);
                //2.3 推送数据
                for (Air air : airList) {
                    boolean success = pushUtil.push(air);
                    if (!success) {
                        // 3.1 代表推送失败，需要落库做好数据的留存，后面该重试
                    }
                }
                log.info("【推送数据】 当前线程推送完毕数据。");
                return "OK";
            });
            futureList.add(future);
        }
        for (Future future : futureList) {
            future.get();
        }
        // 3.2 差选推送失败的数据库表的中信息，可以再次尝试推送（这里推送成功的，可以直接删除，失败的继续留存。）
        CopyOnWriteArrayList<Air> airs = pushUtil.get();
        System.out.println(airs.size());
    }

    /*@Override
    public void pushData() {
        long start = System.currentTimeMillis();

        // 推送失败数据
        List<Air> errorData = new ArrayList<>();
        // 查询需要推送数据。
        List<Air> airList = airMapper.findAll();
        log.info("【推送数据】 推送air数据条数 = {}",airList.size());
        for (Air air : airList) {
            boolean success = pushUtil.push(air);
            if(!success){
                errorData.add(air);
                // 插入推送失败日志。
            }
        }
        long end = System.currentTimeMillis();
        log.info("【推送数据】 推送air数据条数 = {}，花费时间 = {}",airList.size() - errorData.size(),(end - start) / 1000);
    }*/
}
