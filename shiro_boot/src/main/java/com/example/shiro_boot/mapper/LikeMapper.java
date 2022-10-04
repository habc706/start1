package com.example.shiro_boot.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface LikeMapper {


    @Select("select count(*) from `like` where islike=1 and id=#{postid}")
    public Integer query_nums(Long postid);

    @Update("update `like` set islike=#{like} where id=#{postid} and owner=#{uuid}")
    public Integer change_like(@Param("postid") Long postid,@Param("uuid") Long uuid,@Param("like") Boolean like);

    @Select("select islike from `like` where id=#{postid} and owner=#{uuid}")
    public Boolean quert_like(@Param("postid") Long postid, @Param("uuid") Long uuid);


    @Insert("insert into `like` (id,owner) values(#{postid},#{uuid})")
    public Integer add_like(@Param("postid") Long postid,@Param("uuid") Long uuid);

    @Select("select owner from `like` where id=#{postid}")
    List<Long> who_likes(String postid);

}
