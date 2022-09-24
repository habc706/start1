package com.guo.res;

public interface ResCode {
    public static Integer SUCCESS = 200; //成功

    public static Integer ERROR = 201; //失败

    public static Integer NOPEOPLE=202;

    public static  Integer GLOBALEXCEPTION=1000;//全局异常处理

    public static  Integer NOTLOGIN=1001;  //没有登陆

    public static Integer TOKEN_NOT_FOUND=1002; //token不存在

    //没有激活邮箱
    public static Integer EMAIL_NOT_ACTIVE=1003;


}
