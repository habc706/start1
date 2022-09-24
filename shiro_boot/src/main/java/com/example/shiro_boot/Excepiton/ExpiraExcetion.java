package com.example.shiro_boot.Excepiton;


public class ExpiraExcetion extends Exception{
    String  message;
    public ExpiraExcetion(String message){
        this.message=message;
    }
}
