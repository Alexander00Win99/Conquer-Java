package com.conquer_java.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // 服务端启动类——可以接收注册请求
public class EurekaServer_8888 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer_8888.class, args);
    }
}
