package com.conquer_java.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 扩展SpringMVC，此处千万不能添加@EnableWebMvc接口，否则SpringBoot全面接管SpringMVC
// WebMvcConfigurer接口内部方法全部都是default方法，因此可以自主选择任意个数的方法进行实现
@Configuration
@EnableWebMvc
public class MyWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/view-pattern").setViewName("customized-view");
    }
}
