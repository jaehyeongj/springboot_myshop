package com.example.springboot_shop.exception;

public class NotFoundException extends RuntimeException{

    public static class NotFoundRefreshTokenException extends NotFoundException {}
    public static class NotFoundDiscordNameException extends NotFoundException {}
    public static class NotFoundMemberException extends NotFoundException {}
    public static class NotFoundRoomException extends NotFoundException {}
    public static class APPLYNOTAPPLIED extends NotFoundException {}


}