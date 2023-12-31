package com.lime.limetest.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class SessionInterceptor implements AsyncHandlerInterceptor {
    // 컨트롤러 요청(request)이 전달되기 전에 처리
    // 로그인 전에 처리하는 메소드

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle()");

        // 세선에 로그인 정보가 있는지 확인하기 위해 session 구하기.
        HttpSession session = request.getSession();
        if (session.getAttribute("mb") == null){
            // 로그인을 안한 상태
            log.info("Intercept! - Not logged in.");

            response.sendRedirect("/main");
            return false;
        }
        return true;
    }

    // 로그아웃 후에 처리하는 메소드

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle()");

        // 현재 사용하는 웹 프로토콜(http) 버전 1.1 & 1.0 혼용
        if (request.getProtocol().equals("HTTP/1.1")){
            // 1.1 버전일 경우
            response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
        } else {
            // 그 외의 경우
            response.setHeader("Pragma","no-cache");
        }
    }
}
