package com.sqf.springcloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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

//    @Bean
//    @ConfigurationProperties(prefix = "rest.template.config")
//    public HttpComponentsClientHttpRequestFactory customHttpRequestFactory() {
//        return new HttpComponentsClientHttpRequestFactory();
//    }
//
//    @Bean
//    @LoadBalanced //开启负载均衡(ribbon新版本springcloud弃用)
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return new RestTemplate(customHttpRequestFactory());
//    }
//
//    // 以下为新增
//    // 随机轮询loadbalancer
//    @Bean
//    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment,
//                                                            LoadBalancerClientFactory loadBalancerClientFactory) {
//        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        // 随机轮询策略
//        return new RandomLoadBalancer(loadBalancerClientFactory
//                .getLazyProvider(name, ServiceInstanceListSupplier.class),
//                name);
//    }

}
