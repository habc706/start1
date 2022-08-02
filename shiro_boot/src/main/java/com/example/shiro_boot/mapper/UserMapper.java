package com.example.shiro_boot.mapper;

import com.example.shiro_boot.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {

    @Insert("insert into user (uuid,address,contact,name) values (" +
            "#{uuid},#{address},#{contact},#{name})")
    public int add_user(User user);

    //更新操作
    @Update("update user set address=#{address},contact=#{contact},name=#{name}" +
            "where uuid=#{uuid}")
    public int change_user_info(User user);

    //返回个人详细信息
    @Select("select * from user where uuid=#{id}")
    public User query_by_id(String id);

}
