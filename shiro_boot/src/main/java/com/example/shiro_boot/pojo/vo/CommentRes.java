package com.example.shiro_boot.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentRes {
    private String content;
    private Long owner;
    private Date time;
    private String name;
}
