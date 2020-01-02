package com.bl.rbac.start.interceptor;

import com.alibaba.fastjson.JSON;
import com.bl.rbac.start.annotation.OperationResource;
import com.bl.rbac.start.annotation.RbacScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class OperationSourceInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(OperationSourceInterceptor.class);

    private List<String> packages = new ArrayList<>();


    public OperationSourceInterceptor (AnnotationMetadata importAnnotation){
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(
                importAnnotation.getAnnotationAttributes(RbacScan.class.getName()));

        for(String apk : annotationAttributes.getStringArray("value")){
            if(StringUtils.hasText(apk)){
                packages.add(apk);
            }
        }

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("请求进入拦截器....进行鉴权操作..");
        //校验rbacScan扫描的包为空说明不需要权限验证 直接返回
        if(packages.isEmpty()){
            return true;
        }

        Boolean flag = false;
        //校验请求头
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //获取请求方法的路径
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        String name = method.getDeclaringClass().getName();
        //校验请求要执行的方法是否属于rbacScan扫描范围
        for(String packageName : packages){
            if(name.startsWith(packageName)){
                flag = true;
                break;
            }
        }
        //不需要权限验证直接返回
        if(!flag){
            return true;
        }
        //获取方法上的权限注解信息
        OperationResource operationResource = method.getAnnotation(OperationResource.class);
        log.info("请求方法上的权限注解信息{}",JSON.toJSONString(operationResource));
        if(operationResource == null){
            return true;
        }

        String token = request.getHeader("token");

        log.info("请求头携带的token:{}",token);

        //todo 通过请求头携带的token  查出该用户拥有的权限，

        //todo 通过token获取该用户拥有的权限资源 与该方法发上的权限资源比较
        //todo 该用户拥有该方法上的权限  则继续进行，反之返回无权限；

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


}
