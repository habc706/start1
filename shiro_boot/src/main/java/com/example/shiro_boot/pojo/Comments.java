package com.example.shiro_boot.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Comments {
    private String id;
    private String content;
    private String owner;
    private Date time;
}
