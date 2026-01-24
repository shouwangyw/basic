package com.mashibing.controller;

import com.mashibing.entity.Air;
import com.mashibing.service.AirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zjw
 * @description
 */
@RestController
public class AirController {

    @Autowired
    private AirService airService;

    @GetMapping("/air/{id}")
    public Air getById(@PathVariable Long id) {
        return airService.getById(id);
    }
}
