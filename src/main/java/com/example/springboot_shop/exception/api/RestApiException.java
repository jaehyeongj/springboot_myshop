package com.example.springboot_shop.exception.api;

import lombok.Getter;

@Getter
public class RestApiException {
    private int code;
    private String errorMessage;

    public RestApiException(Status status) {
        this.code = status.getCode();
        this.errorMessage = status.getMsg();
    }
}
