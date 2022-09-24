package com.example.shiro_boot.mapper;

import com.example.shiro_boot.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface
UserMapper {

    @Insert("insert into user (uuid,name,password,email,active_code) values (" +
            "#{uuid},#{name},#{password},#{email},#{active_code})")
    public int add_user(User user);

    //更新操作
    @Update("update user set address=#{address},contact=#{contact},name=#{name}" +
            "where uuid=#{uuid}")
    public int change_user_info(User user);

    //返回个人详细信息
    @Select("select * from user where uuid=#{id}")
    public User query_by_id(String id);


    @Select("select active_code from user where email=#{email}")
    public Integer query_active(String  email);

    @Update("update user set activation=true where email=#{email}")
    public int active(String email);

    @Select("select activation from user where email=#{email}")
    public boolean if_active(String  email);


}
