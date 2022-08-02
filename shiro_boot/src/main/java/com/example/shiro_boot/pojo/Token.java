package com.example.shiro_boot.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Token {
    private String token;
    private String uuid;
    private String add_time;
    private String expiration_time;

}
