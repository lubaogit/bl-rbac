package com.bl.rbac.common.exception;

public class RbacAuthException extends RuntimeException {

   private String code;

   private String msg;


   public RbacAuthException(String msg){
       super(msg);
   }

    public RbacAuthException(String code,String msg){
       super(msg);
       this.code =code;
       this.msg =msg;
   }
    public RbacAuthException(String code, String msg,Throwable t){
       super(msg,t);
       this.msg=msg;
       this.code = code;
    }

    public RbacAuthException(RbacErrorCode rbacErrorCode){
       this(rbacErrorCode.getCode(), rbacErrorCode.getMsg());

    }

    public RbacAuthException(RbacErrorCode rbacErrorCode, Throwable t){
       this(rbacErrorCode.getCode(), rbacErrorCode.getMsg(),t);
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
