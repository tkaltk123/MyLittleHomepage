package com.yunseojin.MyLittleHomepage.v2.config.web;

import com.yunseojin.MyLittleHomepage.v2.auth.service.AuthenticationPrincipal;
import com.yunseojin.MyLittleHomepage.v2.auth.service.JwtTokenProvider;
import com.yunseojin.MyLittleHomepage.v2.config.web.interceptor.JwtInterceptor;
import com.yunseojin.MyLittleHomepage.v2.config.web.resolver.LoginUserArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@RequiredArgsConstructor
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.name}")
    private String accessTokenName;

    @Value("${jwt.access.expirationMilli}")
    private Integer accessTokenExpirationMilli;

    @Value("${jwt.refresh.name}")
    private String refreshTokenName;

    @Value("${jwt.refresh.expirationMilli}")
    private Integer refreshTokenExpirationMilli;

    @Value("${jwt.loginUserAttribute}")
    private String loginUserAttr;

    private final AuthenticationPrincipal principal;

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(secret, accessTokenName, accessTokenExpirationMilli,
                refreshTokenName, refreshTokenExpirationMilli,
                principal);
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor(accessTokenName, loginUserAttr, jwtTokenProvider());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor()).order(1);
    }

    @Bean
    public LoginUserArgumentResolver loginUserArgumentResolver() {
        return new LoginUserArgumentResolver(loginUserAttr);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver());
    }
}
