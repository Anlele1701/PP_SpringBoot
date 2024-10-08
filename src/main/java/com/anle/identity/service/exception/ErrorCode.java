package com.anle.identity.service.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EXISTED(401, "User Existed", HttpStatus.INTERNAL_SERVER_ERROR),
    UNCATEGORIZED_EXCEPTION(408, "Uncategorized Exception",  HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(401, "Username must be at least 3 characters",  HttpStatus.BAD_REQUEST),
    KEY_INVALID(400, "Key is invalid", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(401, "Password must be at least 8 characters",  HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(401, "User is not existed",  HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(401, "User is not authenticated",  HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(401, "Do not have permission", HttpStatus.FORBIDDEN);

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
