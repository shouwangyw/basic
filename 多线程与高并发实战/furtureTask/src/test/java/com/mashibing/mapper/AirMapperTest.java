package com.mashibing.mapper;

import com.mashibing.entity.Air;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirMapperTest {

    @Resource
    private AirMapper airMapper;

    @Test
    public void findAll() {
    }

    @Test
    public void selectCountNeedPush() {
        long count = airMapper.selectCountNeedPush("2024-05-21");
        System.out.println(count);
    }

    @Test
    public void selectOneNeedPush() {
        Long id = airMapper.selectOneNeedPush("2024-05-21");
        System.out.println(id);
    }

    @Test
    public void selectByIdAndLimit() {
        List<Air> airList = airMapper.selectByIdAndLimit("2024-05-21", 67, 10066);
        System.out.println(airList.size());
    }
}