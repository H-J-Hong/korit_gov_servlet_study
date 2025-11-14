package com.korit.korit_gov_servlet_study.ch07.service;

import com.korit.korit_gov_servlet_study.ch07.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch07.entity.User;
import com.korit.korit_gov_servlet_study.ch07.repository.UserRepository;

public class UserService {
    UserRepository userRepository = UserRepository.getInstance();

    private static UserService instance;

    private UserService() {}

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User signin(SignupReqDto signupReqDto) {
        return userRepository.addUser(signupReqDto);
    }
}
