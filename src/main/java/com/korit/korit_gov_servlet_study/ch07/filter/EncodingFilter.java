package com.korit.korit_gov_servlet_study.ch07.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter ("/ch07/*")
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[EncodingFilter] 필터 생성");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
    }

    @Override
    public void destroy() {
        System.out.println("[EncodingFilter] 필터 소멸");
    }
}
