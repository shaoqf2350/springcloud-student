package com.sqf.myscspringcloud.controller;

import com.sqf.myscspringcloud.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @Description: 申明式客户端(Ribbon+Hystrix)
 * @Author: shaoqingfeng
 * @CREATE: 2023/11/29 10:41
 */
@RestController
public class FeignController {

    @Autowired
    private FeignService feignService;

    @RequestMapping(value = "/hello", method = POST)
    public String hello (String name){

        String str = feignService.hello(name);

        return str;
    }

}
