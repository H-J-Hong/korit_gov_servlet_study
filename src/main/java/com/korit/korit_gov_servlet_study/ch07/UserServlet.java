package com.korit.korit_gov_servlet_study.ch07;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.korit.korit_gov_servlet_study.ch07.dto.ResponseDto;
import com.korit.korit_gov_servlet_study.ch07.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch07.entity.User;
import com.korit.korit_gov_servlet_study.ch07.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ch07/users")
public class UserServlet extends HttpServlet {
    UserService userService;
    Gson gson;

    @Override
    public void init() throws ServletException {
        userService = UserService.getInstance();
        gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("[UserServlet] 초기 생성 성공");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignupReqDto signupReqDto = gson.fromJson(req.getReader(),SignupReqDto.class);
        User user = userService.signin(signupReqDto);
        System.out.println(signupReqDto);
        System.out.println(user);
        if (user == null) {
            System.out.println("사용자 추가에 실패하였습니다.");
            ResponseDto<User> responseDto = ResponseDto.<User>builder()
                    .status(HttpServletResponse.SC_BAD_REQUEST)
                    .message("사용자 추가에 실패하였습니다.")
                    .body(null)
                    .build();
            resp.getWriter().write(gson.toJson(responseDto));
        } else {
            System.out.println("사용자가 추가되었습니다.");
            ResponseDto<User> responseDto = ResponseDto.<User>builder()
                    .status(HttpServletResponse.SC_OK)
                    .message("가입에 성공하였습니다.")
                    .body(user)
                    .build();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
