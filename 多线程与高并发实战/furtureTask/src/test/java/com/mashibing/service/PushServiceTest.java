package com.mashibing.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushServiceTest {

    @Autowired
    private PushService pushService;

    @Test
    public void pushData() throws ExecutionException, InterruptedException {
        pushService.pushData("2024-05-21");
    }
}