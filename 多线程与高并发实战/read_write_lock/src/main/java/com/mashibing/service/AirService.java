package com.mashibing.service;

import com.mashibing.entity.Air;

import java.util.List;

/**
 * @author zjw
 * @description
 */
public interface AirService {
    Air getById(Long id);

    long findCount();

    List<Air> findLimit(long offset, long count);
}
