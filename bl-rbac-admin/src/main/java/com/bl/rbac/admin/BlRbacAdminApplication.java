package com.bl.rbac.admin;

import com.bl.rbac.admin.config.AdminConfig;
import com.bl.rbac.start.annotation.RbacScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import(AdminConfig.class)
@RbacScan(value = "com.bl.rbac.admin.controller")
public class BlRbacAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlRbacAdminApplication.class, args);
    }

}
