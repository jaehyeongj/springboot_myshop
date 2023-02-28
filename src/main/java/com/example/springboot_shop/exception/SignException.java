package com.example.springboot_shop.exception;

public class SignException extends RuntimeException {

    public static class InvalidEmail extends SignException {

    }

    public static class InvalidPassword extends SignException {

    }

    public static class InvalidNickname extends SignException {

    }

}
