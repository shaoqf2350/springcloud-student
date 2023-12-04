package com.sqf.myscspringcloud.fallback;

import com.sqf.myscspringcloud.service.FeignService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: shaoqingfeng
 * @CREATE: 2023/12/4 20:10
 */
@Component // 加入spring容器，成为spring容器中的一个Bean
public class MyFallbackFactory implements FallbackFactory<FeignService> {

    @Override
    public FeignService create(Throwable throwable){
        return new FeignService(){
            @Override
            public String hello(String name){
                return throwable.getMessage();
            }
        };
    }
}
