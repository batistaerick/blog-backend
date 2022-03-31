package com.erick.blog.exceptions;

public class UserException extends RuntimeException {

    public UserException(Throwable throwable) {
        super(throwable);
    }

    public UserException(String msg) {
        super(msg);
    }
}