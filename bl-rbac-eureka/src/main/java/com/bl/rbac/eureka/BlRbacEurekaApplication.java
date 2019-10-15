package com.bl.rbac.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BlRbacEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlRbacEurekaApplication.class, args);
    }

}
