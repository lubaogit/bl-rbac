package com.bl.rbac.start.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
public @interface OperationResource {

    enum  ResourceType{
        /**
         * 权限资源类型
         */
        IMAGE,VIDEO,FILE,BUTTON,BUSINESS,MENU
    }
    //资源code
    String code() default "";
    //资源描述
    String desc() default "";
    //资源类型
    ResourceType operationType() default ResourceType.BUSINESS;
    //资源父级code
    String parentCode() default "";

}
