package com.example.shiro_boot.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface LikeMapper {


    @Select("select count(*) from like where like=1")
    public Integer query_nums(String postid);

    @Update("update like set islike=#{like} where id=#{postid} and owner=#{uuid}")
    public Integer change_like(String postid,String uuid,boolean like);

    @Select("select islike from `like` where id=#{postid} and owner=#{uuid}")
    public Boolean quert_like(@Param("postid") String postid, @Param("uuid") String uuid);


    @Insert("insert into `like` (id,owner) values(#{postid},#{uuid})")
    public Integer add_like(String postid,String uuid);

    @Select("select owner form like where id=#{postid}")
    List<String> who_likes(String postid);

}
