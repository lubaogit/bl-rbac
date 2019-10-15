package com.bl.rbac.common.vo;

import java.io.Serializable;
import java.util.Date;

public class ResourceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long resourceId;
    //权限 code
    private String resourceCode;
    //权限描述
    private String desc;
    //权限父级code
    private String resourceParentCode;
    //权限类型
    private String resourceType;
    //权限维度
    private String scope;
    //服务名称
    private String applicationName;

    private Date createTime;

    private Date updateTime;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getResourceParentCode() {
        return resourceParentCode;
    }

    public void setResourceParentCode(String resourceParentCode) {
        this.resourceParentCode = resourceParentCode;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
