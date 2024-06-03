package com.tong.tongojbackendgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.tong")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.tong.tongojbackendserviceclient.service"})
public class TongojBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TongojBackendGatewayApplication.class, args);
    }

}
