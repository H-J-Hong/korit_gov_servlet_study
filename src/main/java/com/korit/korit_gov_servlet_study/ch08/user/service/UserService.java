package com.korit.korit_gov_servlet_study.ch08.user.service;

import com.korit.korit_gov_servlet_study.ch08.user.dao.UserDao;
import com.korit.korit_gov_servlet_study.ch08.user.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch08.user.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static UserService instance;
    private UserDao userDao;

    private UserService() {
        this.userDao = UserDao.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }

    public User getUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    public User signup(SignupReqDto signupReqDto) {
        return userDao.addUser(signupReqDto.toEntity());
    }

    public List<User> getAllUserList() {
        return userDao.findAllUserList();
    }

    public List<User> getUserByKeyword(String keyword) {
        return userDao.findUserByKeyword(keyword);
    }


}
