package com.bl.rbac.api.controller;

import com.alibaba.fastjson.JSON;
import com.bl.rbac.api.services.impl.PermissionServiceImpl;
import com.bl.rbac.common.response.ResponseVO;
import com.bl.rbac.common.vo.ResourceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PermissionController {

    private static Logger log = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionServiceImpl permissionService;

    @RequestMapping("/api/syncPermission")
    public ResponseVO<Boolean> syncPermission(@RequestBody List<ResourceVO> list) {

        log.info("从start同步过来的权限资源：{}", JSON.toJSONString(list));

        return null;
    }

}
