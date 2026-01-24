package com.mashibing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.FutureTask;

/**
 * @author zjw
 * @description
 */
@SpringBootApplication
@MapperScan(basePackages = "com.mashibing.mapper")
public class FutureTaskStarterApp {

    public static void main(String[] args) {
        SpringApplication.run(FutureTaskStarterApp.class,args);
    }
}
