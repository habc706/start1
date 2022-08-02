package com.example.shiro_boot.mapper;

import com.example.shiro_boot.pojo.Token;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

public interface TokenMapper {

    //插入操作
    @Insert("insert into token (token,uuid,add_time,expiration_time) values" +
            "(#{token},#{uuid},#{add_time},#{expiration_time})")
    public int add_token(Token token);


    //更新操作
    @Update("update token set token=#{token},add_time=#{add_time}," +
            "expiration_time=#{expiration_time} where uuid=#{uuid}")
    public int change_token(Token token);


}
