package com.example.shiro_boot.utils;

import com.example.shiro_boot.pojo.vo.LoginRes;

public class UserContainer {
    static final ThreadLocal<LoginRes> tl = new ThreadLocal<>();

    public   static void setUser(LoginRes loginRes){tl.set(loginRes);}
    public static void remove(){tl.remove();}
    public static LoginRes getUser(){

        return tl.get();
    }

}
