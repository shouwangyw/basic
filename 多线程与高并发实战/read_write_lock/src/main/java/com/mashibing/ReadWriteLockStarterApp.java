package com.mashibing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zjw
 * @description
 */
@SpringBootApplication
@MapperScan(basePackages = "com.mashibing.mapper")
public class ReadWriteLockStarterApp {

    public static void main(String[] args) {
        SpringApplication.run(ReadWriteLockStarterApp.class, args);
    }
}
