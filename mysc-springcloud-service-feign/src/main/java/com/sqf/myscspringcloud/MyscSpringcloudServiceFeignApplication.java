package com.sqf.myscspringcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MyscSpringcloudServiceFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyscSpringcloudServiceFeignApplication.class, args);
    }

}
