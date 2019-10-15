package com.bl.rbac.common.vo;

import java.io.Serializable;
import java.util.Map;

public class OAuth2 implements Serializable {

    private static final long serialVersionUID = 1L;

    //token
    private String access_token;
    //token有效时长
    private long access_token_in_time;
    //更新token
    private String refresh_token;
    //更新token有效时长
    private long refresh_token_in_time;

    private Map<String,Object> paramMap;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getAccess_token_in_time() {
        return access_token_in_time;
    }

    public void setAccess_token_in_time(long access_token_in_time) {
        this.access_token_in_time = access_token_in_time;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public long getRefresh_token_in_time() {
        return refresh_token_in_time;
    }

    public void setRefresh_token_in_time(long refresh_token_in_time) {
        this.refresh_token_in_time = refresh_token_in_time;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}