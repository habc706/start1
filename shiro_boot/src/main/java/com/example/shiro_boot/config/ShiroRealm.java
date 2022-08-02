package com.example.shiro_boot.config;

import com.example.shiro_boot.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

public class ShiroRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("auth1"); //最终在数据库中查询权限

//        Subject subject = SecurityUtils.getSubject();
//        User user = (User) subject.getPrincipal();
//        info.setStringPermissions(user.getauth());  // 每个用户的具体权限
        //
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证");
        String name="root";
        String  pass="root";

        UsernamePasswordToken usertoken = (UsernamePasswordToken) authenticationToken;
        if(!usertoken.getUsername().equals(name)){
            return null;  //返回空值就是用户名不存在
        }
//        通过token插叙到具体用户之后，把user存进把用户信息存进subject
        // User user = userservice.getuserbytoken(usertoken.getUsername());
//        return new SimpleAuthenticationInfo(user,pass,"");
        //最后默认密码认证,把用户信息存进subject
        return new SimpleAuthenticationInfo(usertoken,pass,"");



    }
}
