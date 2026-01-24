package com.mashibing.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zjw
 * @description
 */
@Data
public class Air implements Serializable {

    private Long id;

    private int pm25;

    private String monitoringStation;
}
