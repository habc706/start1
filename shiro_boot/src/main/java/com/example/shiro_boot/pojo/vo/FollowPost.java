package com.example.shiro_boot.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class FollowPost {
    private Long owner;
    private String owner_name;
    private String owner_img;
    private String title;
    private Date date;

}
