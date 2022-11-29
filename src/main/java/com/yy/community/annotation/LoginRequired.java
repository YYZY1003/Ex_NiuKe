package com.yy.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//用户是否登录
//判断访问设置 那些可以看到那些不能 需满足条件
//配置拦截类 LoginRequiredInterceptor
@Target(ElementType.METHOD)  //注解类
@Retention(RetentionPolicy.RUNTIME) //注解时长
public @interface LoginRequired {

}
