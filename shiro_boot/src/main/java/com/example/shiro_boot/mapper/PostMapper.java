package com.example.shiro_boot.mapper;

import com.example.shiro_boot.pojo.Post;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PostMapper {



    //从第offset条开始，  查询limit条
    @Select("select * from post ORDER BY DATE limit #{offset},#{limit}")
    public List<Post> query_posts(@Param("offset") Integer offset,@Param("limit") Integer limit);

    @Select("select * from post where postid=#{postid}")
    public Post query_post(Long postid);

    @Insert("insert into post (postid,owner,date,content,title) values (" +
            "#{postid},#{owner},#{date},#{content},#{title})")
    public Integer add_post(Post post);

    @Select("select count(*) from post")
    public Integer query_num();

    @Select("select * from post where owner=#{uuid}")
    public List<Post> query_my_posts(Long uuid);

    @Delete("delete from post where postid=#{postid}")
    public Integer delete(@Param("postid") Long postid);

    @Select("select owner from post where postid=#{postid}")
    public Long whos_post(Long postid);
}
