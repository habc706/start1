package com.example.shiro_boot.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;



public class Goods {
    private String good_owner;
    private String good_name;

    public String getGood_owner() {
        return good_owner;
    }

    public void setGood_owner(String good_owner) {
        this.good_owner = good_owner;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getGood_dis() {
        return good_dis;
    }

    public void setGood_dis(String good_dis) {
        this.good_dis = good_dis;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }

    private String good_dis;
    private String url;
    private String  add_time;

    private boolean have;

}
