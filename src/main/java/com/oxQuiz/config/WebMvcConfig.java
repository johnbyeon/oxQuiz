package com.oxQuiz.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // 여기에 필요한 설정을 추가할 수 있습니다.
    // 예: Interceptor, ViewResolver 등

    // 예시로 MyInterceptor를 등록하는 방법
    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    //     registry.addInterceptor(new MyInterceptor())
    //             .addPathPatterns("/**") // 모든 경로에 대해 적용
    //             .excludePathPatterns("/user/login", "/user/register"); // 로그인 및 회원가입 페이지는 제외
    // }

    private final MyInterceptor myInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/signup");
    }
}
