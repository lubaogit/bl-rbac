package com.bl.rbac.common.exception;

public enum RbacErrorCode {

    SUCCESS("200","成功"),
    ERROR00001("ERROR00001","请先登录"),
    ERROR00002("ERROR00002","Token过期");


    private RbacErrorCode(String code, String msg){
            this.code = code;
            this.msg = msg;
    }

    private String code;

    private String msg;

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

    public static String setErrorMsg(RbacErrorCode rbacErrorCode, Object...param){
        String msg = rbacErrorCode.getMsg();
        return String.format(msg,param);
    }
}
