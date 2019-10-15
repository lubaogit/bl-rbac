package com.bl.rbac.api.services.impl;

import com.bl.rbac.api.services.PermissionService;
import com.bl.rbac.common.vo.ResourceVO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PermissionServiceImpl implements PermissionService {


    @Override
    public void syncPermission(List<ResourceVO> resourceVOList) {
        System.out.println("开始执行权限同步.......");
    }
}
