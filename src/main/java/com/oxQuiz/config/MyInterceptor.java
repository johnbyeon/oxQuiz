package com.oxQuiz.config;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // 요청 처리 전 로직을 여기에 작성합니다.
    HttpSession session = request.getSession();
    Object loginId = session.getAttribute("loginId");
    if (ObjectUtils.isEmpty(loginId)) {
        // 로그인 정보가 없으면 로그인 페이지로  redirect
        response.sendRedirect("/user/login");

    }
    // 로그인 정보가 있으면 요청을 계속 진행
    return true;
    }
}
