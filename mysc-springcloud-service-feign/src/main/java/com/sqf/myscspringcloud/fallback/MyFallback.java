package com.sqf.myscspringcloud.fallback;

import com.sqf.myscspringcloud.service.FeignService;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: shaoqingfeng
 * @CREATE: 2023/12/4 19:30
 */
@Component // 加入spring容器，成为spring容器中的一个Bean
public class MyFallback implements FeignService {

    /**
     * 本地熔断(熔断降级处理)
     * @return
     */
    @Override
    public String hello(String name){

        return "远程服务不可用,熔断降级本地处理......";
    }
}
