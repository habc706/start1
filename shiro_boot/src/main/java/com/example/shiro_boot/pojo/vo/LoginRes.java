package com.example.shiro_boot.pojo.vo;

import lombok.Data;

@Data
public class LoginRes {
    private String token;
    private String name;
    private String uuid;
    private String icon_url;
    private String personality;

}
