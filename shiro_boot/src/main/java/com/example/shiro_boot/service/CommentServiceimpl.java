package com.example.shiro_boot.service;

import com.example.shiro_boot.mapper.CommentMapper;
import com.example.shiro_boot.pojo.Comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentServiceimpl  {
    @Autowired
    CommentMapper commentMapper;

    public Integer add_comment(String postid, String uuid, String comment) {
        Comments comments=new Comments();
        comments.setTime(new Date());
        comments.setContent(comment); comments.setId(postid); comments.setOwner(uuid);
        Integer a=commentMapper.add_comments(comments);

        return a;
    }


    public Integer delete_comment(String postid, String uuid, Date date) {
        Comments comments=new Comments();
        comments.setOwner(uuid);comments.setId(postid);comments.setTime(date);
        Integer integer = commentMapper.delete_comment(comments);

        return integer;
    }

    public List<HashMap<String ,String >> query_comments(String postid) {
        List<Comments> comments = commentMapper.query_comments(postid);
        List<HashMap<String ,String >> res=new LinkedList<>();

        for (Comments com:comments
             ) {
            HashMap<String ,String> mid=new HashMap<>();
            mid.put("user",com.getOwner());
            mid.put("content",com.getContent());
            mid.put("time",com.getTime()+"");
            res.add(mid);
        }


        return res;
    }
}
