package com.sqf.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //开启客户端功能(服务提供方)
public class MyscSpringcloudServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyscSpringcloudServiceProviderApplication.class, args);
    }

}
