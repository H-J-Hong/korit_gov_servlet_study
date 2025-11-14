package com.korit.korit_gov_servlet_study.ch07.dto;

import com.korit.korit_gov_servlet_study.ch07.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupReqDto {
    private String username;
    private String password;
    private String email;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
