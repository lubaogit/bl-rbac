package com.bl.rbac.common.response;

import com.bl.rbac.common.exception.RbacErrorCode;

public class ResponseVO<T> {

    private String code = "200";

    private String msg = "成功";

    private T data;


    public ResponseVO(){};

    public ResponseVO(String code, String msg, T data){
        this.code=code;
        this.msg =msg;
        this.data = data;
    }

    public ResponseVO(T data){
        this.data = data;
    }
    public static <T>ResponseVO<T>success(T data){
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setData(data);
        return responseVO;
    }


    public static <T>ResponseVO<T> fail(RbacErrorCode rbacErrorCode){
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setCode(rbacErrorCode.getCode());
        responseVO.setMsg(rbacErrorCode.getMsg());
        return responseVO;

    }

    public static <T>ResponseVO<T> fail(String code,String msg){
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setCode(code);
        responseVO.setMsg(msg);
        return responseVO;

    }


    public static <T>ResponseVO<T> error(String msg){
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setCode("500");
        responseVO.setMsg(msg);
        return responseVO;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
