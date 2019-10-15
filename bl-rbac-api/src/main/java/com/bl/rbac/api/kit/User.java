package com.bl.rbac.api.kit;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -2139313765004931480L;

    /**
     *     用户id
     */
    private Long userId;

    /**
     * 时间戳
     */
    private Long signTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSignTime() {
        return signTime;
    }

    public void setSignTime(Long signTime) {
        this.signTime = signTime;
    }
}
