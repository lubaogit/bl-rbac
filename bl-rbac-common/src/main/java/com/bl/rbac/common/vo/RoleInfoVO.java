package com.bl.rbac.common.vo;

import java.io.Serializable;
import java.util.Date;

public class RoleInfoVO implements Serializable {

    private static final long serialVersionUID = 6190819344732810174L;

    private Long id;

    private String roleName;

    private String roleDesc;

    private String scope;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;


}
