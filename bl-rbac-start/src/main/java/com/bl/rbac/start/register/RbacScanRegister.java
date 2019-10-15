package com.bl.rbac.start.register;

import com.alibaba.fastjson.JSON;
import com.bl.rbac.common.exception.RbacAuthException;
import com.bl.rbac.common.response.ResponseVO;
import com.bl.rbac.common.vo.ResourceVO;
import com.bl.rbac.start.annotation.OperationResource;
import com.bl.rbac.start.annotation.RbacScan;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.*;


@Configuration
public class RbacScanRegister implements ImportAware {


    private static final Logger log = LoggerFactory.getLogger(RbacScanRegister.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bl.rbac.scope}")
    private String scope;

    @Value("${spring.application.name}")
    private String applicationName;


    private AnnotationMetadata metadata;
    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {
        this.metadata =annotationMetadata;
    }

    /**
     * 启动时运行
     */
    @PostConstruct
    protected void doScan() throws IOException {

        if(StringUtils.isEmpty(scope)){
            throw new IllegalArgumentException("bl.rbac.scope 不能为空");
        }

        log.info("RBAC开始扫描....");
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(RbacScan.class.getName()));

        List<String> packageList = new ArrayList<>();

        for(String pkg : annotationAttributes.getStringArray("value")){
            if(StringUtils.hasText(pkg)){
                    packageList.add(pkg);
            }
        }
        if(packageList.size()<0){
            log.info("package is defined...");
            return;
        }
        boolean sync= annotationAttributes.getBoolean("sync");
        if(!sync){
            //不同步
            return;
        }

        List<Class<?>> classes = new ArrayList<>();
        for(String basePack : packageList){
            log.info("scan basePack {}",JSON.toJSONString(basePack));
            try {
                classes.addAll(findClass(basePack));
            }catch (IOException e){
                log.warn("classes error{}",e);
            }

            this.syncOperationResource(classes);
        }
    }
    private List<Class<?>> findClass(String basePack) throws IOException {
        FastClasspathScanner scanner = new FastClasspathScanner(basePack);
        ScanResult scanResult = scanner.scan();

        List<String> classNames = scanResult.getNamesOfAllStandardClasses();
        List<Class<?>> classes = new ArrayList<>();
        for(String className : classNames){
            log.info("scan className {}",JSON.toJSONString(className));
            try {
                classes.add(Class.forName(className));
            }catch (Throwable t){
                log.warn("Scan className {}", JSON.toJSONString(t));
            }
        }
        return classes;
    }


    //获取方法上的operationResource内容
    private void syncOperationResource(List<Class<?>> classes ){
            if(classes.isEmpty()){
                log.info("scan class  is empty......");
                return;
            }
            List<ResourceVO> funcationList = new ArrayList<>();
            for(Class<?> clazz : classes){
                OperationResource classResource = clazz.getAnnotation(OperationResource.class);
                if(classResource == null || !StringUtils.hasText(classResource.code())){
                    continue;
                }
                ResourceVO resourceVO = new ResourceVO();
                resourceVO.setScope(scope);
                resourceVO.setApplicationName(applicationName);
                resourceVO.setResourceCode(classResource.code());
                resourceVO.setResourceType(classResource.operationType().name());
                resourceVO.setDesc(classResource.desc());
                if(!StringUtils.isEmpty(classResource.parentCode())){
                    resourceVO.setResourceParentCode(classResource.parentCode());
                }
                funcationList.add(resourceVO);
                Method [] methods = clazz.getMethods();
                for(Method method : methods){
                    OperationResource methodResource  = method.getAnnotation(OperationResource.class);
                    if(methodResource == null || !StringUtils.hasText(methodResource.code())){
                        continue;
                    }
                    ResourceVO mr = new ResourceVO();
                    mr.setResourceCode(methodResource.code());
                    mr.setDesc(methodResource.desc());
                    mr.setScope(scope);
                    mr.setApplicationName(applicationName);
                    mr.setResourceType(methodResource.operationType().name());
                    if(!methodResource.parentCode().equals("")){
                        mr.setResourceParentCode(methodResource.parentCode());
                    }else {
                        mr.setResourceParentCode(classResource.code());
                    }
                    funcationList.add(mr);
                }

            }
            log.info("注解扫描完毕，扫描结果{}", JSON.toJSONString(funcationList));
            //同步权限资源
            this.syncPermission(funcationList);
    }

    private void syncPermission(List<ResourceVO> resourceVOS){
        //TODO 将权限资源与现有的进行比对，有重复的抛出异常
        this.checkResource(resourceVOS);
        FuturnRunnable futurnRunnable = new FuturnRunnable() {
            @Override
            public void run() {
                try {
                    //TODO 开始往rbac同步权限资源
                    ResponseVO responseVO =restTemplate.postForObject(
                            "http://127.0.0.1:2003/api/syncPermission",resourceVOS,ResponseVO.class);
                    //同步成功 关闭线程
                    log.info("权限同步至RBAC成功！！！");

                    getFuture().cancel(true);
                }catch (Exception e){
                    // TODO 连接失败
                    log.warn("连接RBAC服务器失败...正在重新连接....");
                }
            }
        };

        //重试连接RBAC..3秒一次
        ScheduledExecutorService executorService = newSingleThreadScheduledExecutor();
        Future<?> future1 = executorService.scheduleAtFixedRate(futurnRunnable, 0, 3, TimeUnit.SECONDS);
        futurnRunnable.setFuture(future1);
    }


    private abstract class FuturnRunnable implements Runnable{

        private Future<?> future;

        public Future<?> getFuture() {
            return future;
        }

        public void setFuture(Future<?> future) {
            this.future = future;
        }
    }

    private Boolean checkResource(List<ResourceVO> list){
        Set<String> set = new HashSet<>();

        list.forEach(x ->{
            String permission = x.getResourceParentCode()+"->"+x.getResourceCode();
            if(!set.add(permission)){
                throw new RbacAuthException("权限Code重复，同步失败:"+permission);
            }
            if(!set.add(x.getResourceCode())){
                throw new RbacAuthException("权限Code重复，同步失败:"+x.getResourceCode());
            }
        });
        return Boolean.TRUE;
    };





















}
