package com.bl.rbac.admin.dao;

import com.bl.rbac.common.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select * from user_info where id = 1")
    UserEntity getUser();
}
