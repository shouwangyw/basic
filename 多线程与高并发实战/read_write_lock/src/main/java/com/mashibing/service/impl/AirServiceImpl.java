package com.mashibing.service.impl;

import com.mashibing.cache.AirCache;
import com.mashibing.entity.Air;
import com.mashibing.mapper.AirMapper;
import com.mashibing.service.AirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjw
 * @description
 */
@Service
public class AirServiceImpl implements AirService {

    @Resource
    private AirMapper airMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    @Lazy
    private AirCache airCache;

    private final String AIR_PREFIX = "air:";


    @Override
    public Air getById(Long id) {
        // 查询Redis缓存之前，先去查询一下JVM中的缓存 ConcurrentHashMap 作为JVM缓存
        Air air = airCache.getAirById(id);
        if (air == null) {
            // 查询数据库之前，查询Redis中的数据
            air = (Air) redisTemplate.opsForValue().get(AIR_PREFIX + id);
            if (air == null) {
                // 查询数据库
                air = airMapper.findById(id);
                redisTemplate.opsForValue().set(AIR_PREFIX + id, air);
            }
        }
        return air;
    }

    @Override
    public long findCount() {
        return airMapper.findCount();
    }

    @Override
    public List<Air> findLimit(long offset, long size) {
        return airMapper.findByLimit(offset, size);
    }
}
