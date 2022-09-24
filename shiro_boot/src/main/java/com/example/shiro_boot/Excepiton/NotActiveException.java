package com.example.shiro_boot.Excepiton;

public class NotActiveException extends Exception{
    String message;
    public NotActiveException(String message){
        this.message=message;
    }
}
