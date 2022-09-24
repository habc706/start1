package com.example.shiro_boot.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Post {
    private String postid;
    private String owner;
    private Date date;
    private String tags;
    private String  content;
}
