package com.conquer_java.springboot.config;

import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolverConfig implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        // 获取RequestURI中的language参数
        String lang = request.getParameter("lang");
        if (!StringUtils.isEmpty(lang)) {
            Locale locale = new Locale(lang.split("_")[0], lang.split("_")[1]);
            System.out.println("Request Locale: " + locale.toString());
            return locale;
        }
        System.out.println("Default Locale: " + Locale.getDefault().toString());
        return Locale.getDefault();
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
