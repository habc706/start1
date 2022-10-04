package com.example.shiro_boot.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Post {
    private Long postid;
    private Long owner;
    private Date date;
    private String tags;
    private String  content;
    private String title;

    public Post(){}
    public Post(Long owner,String content,String title){
        this.owner=owner;
        this.content=content;
        this.title=title;
    }
}
