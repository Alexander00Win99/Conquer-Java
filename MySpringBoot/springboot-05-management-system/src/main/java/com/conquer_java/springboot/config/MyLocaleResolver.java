package com.conquer_java.springboot.config;

import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = Locale.getDefault();
        System.out.println("Default Locale: " + locale.toString());

        String lang = request.getParameter("lang");
        // 若RequestURI包含language参数，则利用其生成
        if (!StringUtils.isEmpty(lang)) {
            locale = new Locale(lang.split("-")[0], lang.split("-")[1]);
            System.out.println("Request Locale: " + locale.toString());
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
