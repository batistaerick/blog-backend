package com.erick.blog.exceptions;

public class PostException extends RuntimeException {

    public PostException(Throwable throwable) {
        super(throwable);
    }

    public PostException(String msg) {
        super(msg);
    }
}