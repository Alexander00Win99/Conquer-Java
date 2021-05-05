package com.conquer_java.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewFilter;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration // 对应application.xml的bean配置
public class MyDruidConfig {
    // 【目的】：自定义Bean代替SpringBoot自动创建的Bean
    @Bean // 1) Step-01：置于IoC Container
    @ConfigurationProperties(prefix = "spring.datasource") // 2) Step-02：绑定配置文件application.yml中的spring.datasource属性区域
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    /**
     * ServletRegistrationBean <==> web.xml配置文件
     * SpringBoot内嵌的Tomcat作为Servlet Container，没有web.xml配置文件，
     * 只能通过注册中心对象ServletRegistrationBean设置各项参数
     *
     * Feature：定制后台监控统计功能
     * 【实现方法】：将Alibaba Druid类StatViewServlet注册到Spring ServletRegistrationBean中心
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // 【定制内容】：后台登录账号密码（遵循Druid初始参数固定key名称配置）
        HashMap<String, String> map = new HashMap<>();
        map.put("loginUser", "admin");
        map.put("loginPassword", "123456");
        map.put("allow", ""); // 为空，允许所有人访问
        map.put("Alex Wen", "12.0.0.1"); // 禁止某人访问
        bean.setInitParameters(map);
        return bean;
    }

    @Bean
    public FilterRegistrationBean druidStatViewFilter() {
        // 不用构造器
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        // 设置Druid过滤器
        bean.setFilter(new StatViewFilter());
        HashMap<String, String> filterParams = new HashMap<>();
        filterParams.put("exclusion", "*.js,*.css,/druid/*");
        // 或者bean.addInitParameter(name, value)逐条手动加入
        bean.setInitParameters(filterParams);
        return bean;
    }
}
