package com.tong.tongojbackendjudgeservice;

import com.tong.tongojbackendjudgeservice.rabbitmq.InitRabbitMQ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.tong")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.tong.tongojbackendserviceclient.service"})
public class TongojBackendJudgeServiceApplication {

    public static void main(String[] args) {
        InitRabbitMQ.doInit();
        SpringApplication.run(TongojBackendJudgeServiceApplication.class, args);
    }

}
