package com.korit.korit_gov_servlet_study.ch07.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto<T> {
    private int status;
    private String message;
    private T body;
}
