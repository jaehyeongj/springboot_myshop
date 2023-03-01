package com.example.springboot_shop.exception;

public class CommonException extends RuntimeException {

    public static class MemberAuthorityException extends CommonException {}

    public static class RefreshTokenNotFoundException extends CommonException {}

    public static class IllegalAgumentException extends CommonException {}

}
