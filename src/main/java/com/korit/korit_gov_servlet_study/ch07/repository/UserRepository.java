package com.korit.korit_gov_servlet_study.ch07.repository;

import com.korit.korit_gov_servlet_study.ch07.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch07.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository instance;
    private int autoIncrement = 0;
    private List<User> userList = new ArrayList<>();

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User addUser(SignupReqDto signupReqDto) {
        User user = signupReqDto.toEntity();
        user.setUserId(autoIncrement++);
        userList.add(user);
        return user;
    }
}
