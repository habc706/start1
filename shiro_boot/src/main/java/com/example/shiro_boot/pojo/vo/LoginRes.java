package com.example.shiro_boot.pojo.vo;

import lombok.Data;

@Data
public class LoginRes {  //返回登陆信息
    private String name;
    private String token;
    private Long uuid;
    private String icon_url;
    private String personality;
}
