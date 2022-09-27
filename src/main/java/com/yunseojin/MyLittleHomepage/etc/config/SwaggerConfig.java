package com.yunseojin.MyLittleHomepage.etc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Value("${jwt.access.name}")
    private String accessTokenName;

    @Value("${jwt.refresh.name}")
    private String refreshTokenName;

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .securityContexts(List.of(securityContext()))
                .securitySchemes(apiKey())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                ;
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("MyLittleHomepage API")
                .description("MyLittleHomepage API Docs")
                .build();
    }

    private SecurityContext securityContext() {

        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {

        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference(accessTokenName, authorizationScopes),
                new SecurityReference(refreshTokenName, authorizationScopes));
    }

    private List<SecurityScheme> apiKey() {

        List<SecurityScheme> list = new ArrayList<>();
        list.add(new ApiKey(accessTokenName, "Bearer +accessToken", "header"));
        list.add(new ApiKey(refreshTokenName, "Bearer +refreshToken", "header"));
        return list;
    }
}