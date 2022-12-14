package com.example.shiro_boot.mapper;

import com.example.shiro_boot.pojo.User;
import com.example.shiro_boot.pojo.vo.UserRes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.javassist.compiler.ast.StringL;

import java.util.Date;

public interface
UserMapper {

    @Insert("insert into user (uuid,name,password,email,create_date) values (" +
            "#{uuid},#{name},#{password},#{email},#{create_date})")
    public int add_user(User user);

    //更新操作
    @Update("update user set name=#{name},personality=#{personality} " +
            "where uuid=#{uuid}")
    public Integer change_user_info(@Param("uuid") Long uuid,@Param("name") String name,@Param("personality") String personality);

    //返回个人详细信息
    @Update("update user set icon_url=#{icon} where uuid=#{id}")
    public Integer update_icon(@Param("id") Long id, @Param("icon") String icon);

    //------------------------
    @Select("select name,icon_url,personality from user where uuid=#{uuid}")
    public UserRes query_user_info(Long uuid);

    @Select("select active_code from user where email=#{email}")
    public Integer query_active(String  email);

    @Update("update user set activation=true where email=#{email}")
    public int active(String email);

    @Select("select activation from user where email=#{email}")
    public boolean if_active(String  email);

    @Select("select name from user where uuid=#{uuid}")
    public String query_name(Long uuid);

    @Select("select create_date from `user` where uuid=#{uuid}")
    public Date query_date(Long uuid);
}
