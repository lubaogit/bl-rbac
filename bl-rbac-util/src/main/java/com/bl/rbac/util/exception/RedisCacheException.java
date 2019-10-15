package com.bl.rbac.util.exception;

import java.io.Serializable;

public class RedisCacheException extends RuntimeException implements Serializable {


    private static final long serialVersionUID = -5749668215473008057L;

    private String code;

    private String msg;

    public RedisCacheException(String msg){super(msg);}


    public RedisCacheException(String code,String msg){
        super(msg);
        this.code=code;
        this.msg = msg;
    }

    public RedisCacheException(String code,String msg,Throwable t){
        super(msg,t);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
