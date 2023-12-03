package com.sqf.springcloud.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Author: shaoqingfeng
 * @CREATE: 2023/11/29 10:46
 */
@Configuration // 等价于一个spring application.xml配置文件
public class BeanConfig {

    @LoadBalanced //开启负载均衡(加入ribbon集中负载均衡)
    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {

        return builder.build();
    }
}
