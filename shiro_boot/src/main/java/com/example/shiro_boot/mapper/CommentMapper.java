package com.example.shiro_boot.mapper;

import com.example.shiro_boot.pojo.Comments;
import com.example.shiro_boot.pojo.vo.CommentRes;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {

    @Insert("insert into comments (id,content,owner,time) values (#{id}," +
            "#{content},#{owner},#{time})")
    public Integer add_comments(Comments comments);

    @Delete("delete from `comments` where time=#{time} and id=#{id} and owner=#{owner}")
    public Integer delete_comment(Comments comments);

    @Select("select content,owner,time from comments where id=#{postid}")
    public List<CommentRes> query_comments(String postid);

//    @Select("select owner from comments where id=#{postid}")
//    public List<String > query_who_comment(String postid);

}
