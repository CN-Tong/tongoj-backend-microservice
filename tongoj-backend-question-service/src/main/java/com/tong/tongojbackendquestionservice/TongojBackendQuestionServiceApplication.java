package com.tong.tongojbackendquestionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@MapperScan("com.tong.tongojbackendquestionservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.tong")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.tong.tongojbackendserviceclient.service"})
public class TongojBackendQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TongojBackendQuestionServiceApplication.class, args);
    }

}
