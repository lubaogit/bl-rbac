package com.bl.rbac.api.services;

import com.bl.rbac.common.vo.ResourceVO;

import java.util.List;

public interface PermissionService {

   public void syncPermission(List<ResourceVO> resourceVOList);
}
