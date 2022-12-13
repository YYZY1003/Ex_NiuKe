package com.yy.community.config;

import com.yy.community.controller.intercepetor.AlphaInterceptor;
import com.yy.community.controller.intercepetor.LoginRequiredInterceptor;
import com.yy.community.controller.intercepetor.LoginTicketInterceptor;
import com.yy.community.controller.intercepetor.MessageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AlphaInterceptor alphaInterceptor;

    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Autowired
    private MessageInterceptor messageInterceptor;

    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(alphaInterceptor).
                excludePathPatterns("/**/*.css","/**/*.png","/**/*.jpeg","/**/*.js").
                addPathPatterns("/register","/login");

        registry.addInterceptor(loginTicketInterceptor).
                excludePathPatterns("/**/*.css", "/**/*.png", "/**/*.jpeg", "/**/*.js", "/**/*.jpg");

        registry.addInterceptor(loginRequiredInterceptor).
                excludePathPatterns("/**/*.css", "/**/*.png", "/**/*.jpeg", "/**/*.js");

        registry.addInterceptor(messageInterceptor).
                excludePathPatterns("/**/*.css", "/**/*.png", "/**/*.jpeg", "/**/*.js");

    }
}



