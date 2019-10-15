package com.bl.rbac.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String userId;

    private String userName;

    private String userRealName;

    private String mobile;

    private String addree;

    private Integer age;

    private String password;

    private String IdCardNo;

    private String sex;

    private String status;

    private Date createDate;

    private String create_user;

    private Date updateDate;

    private String upateUser;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddree() {
        return addree;
    }

    public void setAddree(String addree) {
        this.addree = addree;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdCardNo() {
        return IdCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        IdCardNo = idCardNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
