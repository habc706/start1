package com.example.shiro_boot.config;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("getdefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);
        Map<String,String> filtermap = new LinkedHashMap<>();
        filtermap.put("/","anon");
        filtermap.put("/a","authc");
        filtermap.put("/a2","authc");
        filtermap.put("/good/*","authc");
        filtermap.put("/needa","perms[auth1]");
        filtermap.put("/api.v1","authc");
        bean.setFilterChainDefinitionMap(filtermap);
        bean.setLoginUrl("/login/login");

        bean.setUnauthorizedUrl("/unauthor");

        return bean;
    }

    @Bean
    public DefaultWebSecurityManager getdefaultWebSecurityManager(@Qualifier("Realm")ShiroRealm shiroRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    @Bean
    public ShiroRealm Realm(){
        return new ShiroRealm();
    }

}

