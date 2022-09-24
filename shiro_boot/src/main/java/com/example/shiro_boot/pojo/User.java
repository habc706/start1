package com.example.shiro_boot.pojo;

import lombok.Data;

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
    public User(String uuid,String name,String password,String email,Integer active_code){
        this.uuid=uuid;
        this.name=name;
        this.password=password;
        this.email=email;
        this.active_code=active_code;
    }
    public User(){
    }


}
