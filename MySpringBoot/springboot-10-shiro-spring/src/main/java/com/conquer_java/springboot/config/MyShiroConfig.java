package com.conquer_java.springboot.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyShiroConfig {
    // STEP-03: 创建ShiroFilterFatoryBean对象(依赖DefaultWebSecurityManager对象)
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("manager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);

        // 添加Shiro内置过滤器
        /**
         * 1) anon AnonymousFilter 匿名访问；
         * 2) authc FormAuthenticationFilter 表单拦截器：包括：usernameParam、passwordParam、rememberMeParam、loginUrl、successUrl、failureKeyAttribute；
         * 3) user userFilter 用户拦截器：用户已经完成身份验证/选择remember-me功能方可使用；
         * 4) perms PermissionsAuthorizationFilter 授权拦截器：用户拥有资源访问权限方可使用，例如：“/user/**=perms[“user:create”]”；
         * 5) rest HttpMethodPermissionFilter rest风格拦截器：
         * 6) port
         * 7) ssl
         * 8)
         * 9)
         */
        return bean;
    }

    // STEP-02: 创建DefaultWebSecurityManager对象(依赖MyUserReaml对象)
    @Bean(name = "manager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("realm") MyUserRealm myUserRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myUserRealm);
        return manager;
    }

    // STEP-01: 创建Realm对象
    //@Bean // 如果不用name参数，那么默认名称是myUserRealmm
    @Bean(name = "realm")
    public MyUserRealm myUserRealm() {
        return new MyUserRealm();
    }
}
