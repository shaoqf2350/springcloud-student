package com.sqf.springcloud;

import com.sqf.springcloud.config.BeanConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableEurekaClient //开启客户端功能(服务消费方)
@EnableHystrix

// @SpringCloudApplication(过时) = SpringBootApplication + EnableEurekaClient + EnableHystrix
//@LoadBalancerClients(value = {
//        @LoadBalancerClient(name = "provider-server", configuration = BeanConfig.class),
//                })
public class MyscSpringcloudServiceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyscSpringcloudServiceConsumerApplication.class, args);
    }

}
