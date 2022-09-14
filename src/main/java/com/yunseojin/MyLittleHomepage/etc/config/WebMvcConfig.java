package com.yunseojin.MyLittleHomepage.etc.config;

import com.yunseojin.MyLittleHomepage.etc.interceptor.LoginInterceptor;
import com.yunseojin.MyLittleHomepage.etc.interceptor.RestLoginInterceptor;
import com.yunseojin.MyLittleHomepage.etc.resolver.MemberTokenArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final RestLoginInterceptor restLoginInterceptor;
    private final MemberTokenArgumentResolver memberTokenArgumentResolver;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", "classpath:/templates/", "classpath:/templates/layout/", "classpath:/static/bootstrap/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**", "/api/**");

        registry.addInterceptor(restLoginInterceptor)
                .addPathPatterns("/api/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        argumentResolvers.add(memberTokenArgumentResolver);
    }
}