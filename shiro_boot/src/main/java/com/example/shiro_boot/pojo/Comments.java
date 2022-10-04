package com.example.shiro_boot.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Comments {
    private Long id;
    private String content;
    private Long owner;
    private Date time;
}
