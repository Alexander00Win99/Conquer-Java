package com.conquer_java.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

//import com.axa.pricingplatform.constants.CommonConstant;

@Configuration
//@EnableWebMvc // 必须取消，否则导致整个SpringBoot自动配置全部崩盘！
public class MyWebMvcConfig implements WebMvcConfigurer {
    static class MyLocaleResolver implements LocaleResolver {
        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            // 1) 首选根据获取的RequestURI中的language参数解析语言选项
            String lang_request = request.getParameter("lang");
            if (!StringUtils.isEmpty(lang_request)) {
                Locale locale = new Locale(lang_request.split("_")[0], lang_request.split("_")[1]);
                System.out.println("Request Locale: " + locale.toString());

                /**
                 * 如下导致堆栈溢出错误：
                 * Servlet.service() for servlet [dispatcherServlet] in context with path [/springboot] threw exception [Handler processing failed; nested exception is java.lang.StackOverflowError] with root cause
                 * java.lang.StackOverflowError: null
                 * locale = RequestContextUtils.getLocale(request);
                 * System.out.println("Request Locale: " + locale.getLanguage() + " + " + locale.getCountry());
                 */

                //request.getSession().setAttribute(CommonConstant.LOGIN_LANG, lang_request);
                return locale;
            }

            // 2) 其次根据请求的Session中的language属性解析语言选项
            //String lang_session = request.getSession().getAttribute(CommonConstant.LOGIN_LANG);
            //if (!StringUtils.isEmpty(lang_session)) {
                //return new Locale(lang_session.split("_")[0], lang_session.split("_")[1]);
            //}

            // 3) 最后返回本地默认语言选项
            System.out.println("Default Locale: " + Locale.getDefault().toString());
            return Locale.getDefault();
        }

        @Override
        public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

        }
    }

    static class MyLoginHandlerInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            // 获取用户session信息
            Object loginUser = request.getSession().getAttribute("loginUser");
            if (loginUser == null) {
                request.setAttribute("msg", "尚无登录，无权进入！");
                request.getRequestDispatcher("/index.html").forward(request, response);
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.htm").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/homepage.htm").setViewName("index");
        registry.addViewController("/homepage.html").setViewName("index");
        // 新增映射：main.html请求 -> dashboard.html页面（将其绑定视图dashboard）
        // 1) 此处存在一个问题，用户URL地址栏直接输入main.html也可进入dashboard页面，因此需要实现拦截器用于拦截访问请求；
        // 2) 浏览器URL地址栏输入RequestURL，并非一定访问页面main.html，可能main.html只是一个请求URL刚好名为main.html；
        registry.addViewController("/main.htm").setViewName("dashboard");
        registry.addViewController("/main.html").setViewName("dashboard");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyLoginHandlerInterceptor())
                .addPathPatterns("/**")
                //.excludePathPatterns("/", "/index.html", "/user/login");
                .excludePathPatterns("/", "/index.html", "/user/login", "*.css", "*.js");
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}
