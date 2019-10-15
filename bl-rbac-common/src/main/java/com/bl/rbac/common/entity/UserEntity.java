package com.bl.rbac.common.entity;

import java.io.Serializable;

public class UserEntity implements Serializable {


    private static final long serialVersionUID = -2936954371521887025L;
    private String user_name;

    private Long id;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
