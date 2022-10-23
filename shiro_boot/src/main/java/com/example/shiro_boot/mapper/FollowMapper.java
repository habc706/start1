package com.example.shiro_boot.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface FollowMapper {

    @Select("SELECT COUNT(*) FROM fllow WHERE `user_id`=#{myid} AND `fllow_user_id`=#{follow_id}")
    public Integer query_if(@Param("myid") Long myid, @Param("follow_id") Long follow_id);


    @Insert("INSERT INTO fllow (`user_id`,`fllow_user_id`) VALUES(#{myid},#{user_id})")
    public  Integer insert(@Param("myid")Long myid,@Param("user_id")Long user_id);

    @Delete("DELETE FROM `fllow` WHERE `user_id`=#{myid} AND `fllow_user_id`=#{user_id}")
    public   Integer delete(@Param("myid")Long myid,@Param("user_id")Long user_id);

}
