package com.korit.korit_gov_servlet_study.ch08.user.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.korit.korit_gov_servlet_study.ch08.user.dto.ApiRespDto;
import com.korit.korit_gov_servlet_study.ch08.user.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch08.user.entity.User;
import com.korit.korit_gov_servlet_study.ch08.user.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ch08/user")
public class UserServlet extends HttpServlet {
    private UserService userService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = UserService.getInstance();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String keyword = req.getParameter("keyword");
        ApiRespDto<?> apiRespDto = null;
        if (username != null) {
            User user = userService.getUserByUsername(username);
            if (user != null) {
                apiRespDto = ApiRespDto.<User>builder()
                        .status("success")
                        .message("유저네임 : " +  username + " 의 조회에 성공하였습니다.")
                        .body(user)
                        .build();
            } else {
                apiRespDto = ApiRespDto.<User>builder()
                        .status("failed")
                        .message("유저네임 : " + username + " 의 조회에 실패하였습니다.")
                        .body(user)
                        .build();
            }
        } else if(keyword !=null) {
            List<User> userList = userService.getUserByKeyword(keyword);
            if (!userList.isEmpty()) {
                apiRespDto = ApiRespDto.<List<User>>builder()
                        .status("success")
                        .message("키워드 : " + keyword + " 의 조회에 성공하였습니다.")
                        .body(userList)
                        .build();
            } else {
                apiRespDto = ApiRespDto.<List<User>>builder()
                        .status("failed")
                        .message("키워드 : " + keyword + " 의 조회에 실패하였습니다.")
                        .body(userList)
                        .build();
            }

        } else {
            List<User> userList = userService.getAllUserList();
            if (!userList.isEmpty()) {
                apiRespDto = ApiRespDto.<List<User>>builder()
                        .status("success")
                        .message("전체 조회에 성공하였습니다.")
                        .body(userList)
                        .build();
            }
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(resp.getWriter(),apiRespDto);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignupReqDto signupReqDto = objectMapper.readValue(req.getReader(), SignupReqDto.class);
        ApiRespDto<?> apiRespDto = null;

        if (userService.getUserByUsername(signupReqDto.getUsername()) != null) {
            apiRespDto = ApiRespDto.<String>builder()
                    .status("failed")
                    .message(signupReqDto.getUsername() + "은 이미 사용중인 username 입니다.")
                    .body(signupReqDto.getUsername())
                    .build();
        }

        User user = userService.signup(signupReqDto);
        if (user != null) {
            apiRespDto = ApiRespDto.<User>builder()
                    .status("success")
                    .message(user.getUsername() + "으로 가입에 성공하였습니다.")
                    .body(user)
                    .build();
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(resp.getWriter(),apiRespDto);
    }
}
