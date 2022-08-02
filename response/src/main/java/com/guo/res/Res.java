package com.guo.res;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Res   {
    private boolean success;

    private Integer code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public Res setMessage(String message) {
        this.message = message;
        return this;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    private String message;

    private HashMap<String ,Object> data = new HashMap<>();

    private Res(){};

    public static Res ok(){
        Res r = new Res();
        r.success=true;
        r.code=ResCode.SUCCESS;

        return r;
    }

    public static Res fail(){
        Res r = new Res();
        r.success=false;
        r.code=ResCode.ERROR;

        return r;
    }

    public Res success(boolean success){
        this.success=success;
        return this;
    }
    public Res code(Integer code){
        this.code=code;
        return this;
    }
    public Res data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public Res data(HashMap<String, Object> map){
        this.data=map;
        return this;
    }


}
