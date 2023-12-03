package com.sqf.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //开启注册中心服务端
public class MyscSpringcloudServiceEurekaApplication8762 {

    public static void main(String[] args) {
        SpringApplication.run(MyscSpringcloudServiceEurekaApplication8762.class, args);
    }

}
