package com.sqf.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard // 开启仪表盘功能
public class SpringcloudHystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudHystrixDashboardApplication.class, args);
    }

}
