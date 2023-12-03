package com.sqf.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //开启注册中心服务端
public class MyscSpringcloudServiceEurekaApplication8761 {

    public static void main(String[] args) {
        SpringApplication.run(MyscSpringcloudServiceEurekaApplication8761.class, args);
    }

}
