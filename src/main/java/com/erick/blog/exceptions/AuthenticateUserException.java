package com.erick.blog.exceptions;

public class AuthenticateUserException extends RuntimeException {
    public AuthenticateUserException(String e) {
        super("Failed to authenticate user" + e);
    }
}