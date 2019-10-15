package com.bl.rbac.start.annotation;


import com.bl.rbac.start.config.EnableRbacStarterConfig;
import com.bl.rbac.start.permission.OperationSourceAutoConfig;
import com.bl.rbac.start.register.RbacScanRegister;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableEurekaClient
@Import({EnableRbacStarterConfig.class, RbacScanRegister.class, OperationSourceAutoConfig.class})
public @interface RbacScan {

    /**
     * 扫包路径
     * @return
     */
    String[] value() default "";

    /**
     * 是否同步权限
     * @return
     */
    boolean sync() default true;

}
