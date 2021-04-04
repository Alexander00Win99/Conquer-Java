package com.conquer_java.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc // 必须取消，否则导致整个SpringBoot自动配置全部崩盘！
public class MyWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.htm").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/homepage.htm").setViewName("index");
        registry.addViewController("/homepage.html").setViewName("index");
    }

    @Bean
    public LocaleResolver myLocaleResolver() {
        return new MyLocaleResolver();
    }
}
