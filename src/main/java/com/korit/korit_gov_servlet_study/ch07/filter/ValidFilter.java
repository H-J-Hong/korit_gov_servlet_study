package com.korit.korit_gov_servlet_study.ch07.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.korit.korit_gov_servlet_study.ch07.dto.SignupReqDto;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebFilter("/ch07/*")
public class ValidFilter implements Filter {
    Gson gson;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[ValidFilter] 필터 생성");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String,String> error = new HashMap<>();
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;


        if ("POST".equals(httpServletRequest.getMethod()) && "/ch07/users".equals(httpServletRequest.getServletPath())) {
            SignupReqDto signupReqDto = gson.fromJson(servletRequest.getReader(),SignupReqDto.class);
            Arrays.stream(signupReqDto.getClass().getDeclaredFields()).forEach(f -> {
                f.setAccessible(true);
                String fName = f.getName();
                    });

        }


    }

    @Override
    public void destroy() {
        System.out.println("[ValidFilter] 필터 소멸");
    }
}
