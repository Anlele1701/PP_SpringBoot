package com.anle.identity.service.exception;

public enum ErrorCode {
    USER_EXISTED(401, "User Existed"),
    UNCATEGORIZED_EXCEPTION(408,"Uncategorized Exception"),
    USERNAME_INVALID(401, "Username must be at least 3 characters"),
    KEY_INVALID(400, "Key is invalid"),
    PASSWORD_INVALID(401, "Password must be at least 8 characters"),
    USER_NOT_EXISTED(401,"User is not existed")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
