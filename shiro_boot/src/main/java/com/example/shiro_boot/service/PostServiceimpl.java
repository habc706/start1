package com.example.shiro_boot.service;

import com.example.shiro_boot.mapper.PostMapper;
import com.example.shiro_boot.pojo.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceimpl  {
    @Autowired
    private PostMapper postMapper;


    public List<Post> get_posts(Integer offset, Integer limit) {

        List<Post> res=postMapper.query_posts(offset,limit);
        return res;
    }


    public Post get_post(String postid) {
        Post post = postMapper.query_post(postid);

        return post;
    }


    public Integer write_post(String uuid, String content, String title) {
        Post post=new Post(uuid,content,title);
        post.setDate(new Date());
        //生成postid 前10位用户id+后6位时间戳
        String last=""+new Date().getTime();
        String postid=uuid.substring(0,10)+last.substring(last.length()-6,last.length());
        post.setPostid(postid);
        Integer res =postMapper.add_post(post);

        return res;
    }
}
