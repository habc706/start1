package com.example.shiro_boot.mapper;


import com.example.shiro_boot.pojo.vo.LoginRes;
import com.example.shiro_boot.pojo.vo.Login_mid;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LoginMapper {



    @Insert("insert into login(token,name,passwd) values(#{token}," +
            "#{name},#{passwd})")
    public int insert_login();

    @Select("select PASSWORD from user where email=#{email}")
    public String query_passwd( String email);

    @Select("select uuid,name from user where email=#{email}")
    public Login_mid query_message(String email);


}
