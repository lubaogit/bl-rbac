package com.bl.rbac.admin.controller;


import com.alibaba.fastjson.JSON;
import com.bl.rbac.admin.dao.UserDao;
import com.bl.rbac.common.entity.UserEntity;
import com.bl.rbac.start.annotation.OperationResource;
import com.bl.rbac.util.cache.UserInfoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@OperationResource(code = "userManager",desc = "用户管理")
public class UserInfoController {

    private static Logger log = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserInfoCache userInfoCache;


    @RequestMapping("/getUserInfo")
    @OperationResource(code = "userInfo",desc = "获取用户信息")
    public String getUserInfo(){

        System.out.println("收集日志...........");
        log.info("ELK收集日志...........");
        UserEntity userEntity = userDao.getUser();

        //加入缓存
        userInfoCache.setUserInfo(userEntity);
        log.info("用户信息：{}",JSON.toJSONString(userEntity));
        return "yoghurt管理";
    }

    @RequestMapping("/updateUserInfo")
    @OperationResource(code = "updateUserInfo",desc = "修改用户信息")
    public String updateUserInfo(){

        System.out.println("收集修改用户日志...........");
        log.info("ELK收集修改用户日志...........");
        //从缓存中获取数据
        UserEntity userEntity = userInfoCache.getUserInfoCache("1");
        log.info("用户信息：{}",JSON.toJSONString(userEntity));
        return JSON.toJSONString(userEntity);
    }


    //试试yixia
}
