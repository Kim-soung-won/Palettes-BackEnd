package com.palette.palettepetsback.config;

import com.palette.palettepetsback.config.jwt.jwtAnnotation.LoginCheckMethodArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckMethodArgumentResolver loginCheckMethodArgumentResolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOriginPatterns("*") //모든 도메인 허용
                .allowedMethods("*") // 모든 HTTP 메서드 허용
                .allowCredentials(true)
                .allowedHeaders("*");// 모든 HTTP 헤더 허용
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginCheckMethodArgumentResolver);
    }
}