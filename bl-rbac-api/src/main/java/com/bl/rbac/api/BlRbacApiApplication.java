package com.bl.rbac.api;

import com.bl.rbac.api.config.ApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import(ApiConfig.class)
public class BlRbacApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlRbacApiApplication.class, args);
    }

}
