package com.example.shiro_boot.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class User {
    private String uuid;
    private String password;
    private String email;
    private String name;
    private String auth;
    private Date create_date;
    private boolean activation;
    private Integer active_code;
    private String icon_url;
    private String personality;
}
