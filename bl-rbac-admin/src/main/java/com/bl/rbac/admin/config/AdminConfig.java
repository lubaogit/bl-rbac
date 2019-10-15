package com.bl.rbac.admin.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.bl.rbac")
@MapperScan({"com.bl.rbac.admin.dao"})
public class AdminConfig {
}
