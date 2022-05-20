package com.erick.blog.exceptions;

public class HandlerException extends RuntimeException {

    public HandlerException(Throwable throwable) {
        super(throwable);
    }

    public HandlerException(String msg) {
        super(msg);
    }
}