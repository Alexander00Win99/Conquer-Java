package com.conquer_java.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

// 扩展SpringMVC，此处千万不能添加@EnableWebMvc接口，否则SpringBoot全面接管SpringMVC
// WebMvcConfigurer接口内部方法全部都是default方法，因此可以自主选择任意个数的方法进行实现
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    // 自行实现ViewResolver接口用作视图解析器
    public static class MyViewResolver implements ViewResolver {
        @Override
        public View resolveViewName(String s, Locale locale) throws Exception {
            return null;
        }
    }

    @Bean
    public ViewResolver myViewResolver() {
        return new MyViewResolver();
    }
}
