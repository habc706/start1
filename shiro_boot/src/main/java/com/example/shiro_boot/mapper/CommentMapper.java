package com.example.shiro_boot.mapper;

import com.example.shiro_boot.pojo.Comments;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {

    @Insert("insert into comments (id,content,owner,time) values (#{id}," +
            "#{content},#{owner},#{time})")
    public Integer add_comments(Comments comments);

    @Delete("delete from comments where date=#{date},id=#{id},owner=#{owner}")
    public Integer delete_comment(Comments comments);

    @Select("select * from comments where id=#{postid}")
    public List<Comments> query_comments(String postid);

//    @Select("select owner from comments where id=#{postid}")
//    public List<String > query_who_comment(String postid);

}
