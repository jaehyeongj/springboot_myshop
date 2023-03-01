package com.example.springboot_shop.exception;

public class ExistsException extends RuntimeException {

    public static class DuplicatedEmail extends ExistsException{}
    public static class AlreadyMemberException extends ExistsException {}
    public static class AlreadyFriendException extends ExistsException {}
    public static class AlreadyApplyFriendException extends ExistsException {}
}
