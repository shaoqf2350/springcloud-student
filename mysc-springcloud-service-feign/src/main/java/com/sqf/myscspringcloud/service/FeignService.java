package com.sqf.myscspringcloud.service;

import com.sqf.myscspringcloud.fallback.MyFallback;
import com.sqf.myscspringcloud.fallback.MyFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 使用Feign的客户端注解绑定远程服务的名称(远程服务名称大小写不区分)
 */
// @FeignClient(value = "mysc-springcloud-service-provider", fallback = MyFallback.class)
@FeignClient(value = "mysc-springcloud-service-provider", fallbackFactory = MyFallbackFactory.class)
public interface FeignService {

    /**
     * 声明一个方法,这个方法就是远程服务提供者提供的那个方法
     * @return
     */
    @RequestMapping("/service/hello")
    public String hello(String name);
}
