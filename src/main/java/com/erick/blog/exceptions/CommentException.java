package com.erick.blog.exceptions;

public class CommentException extends RuntimeException {

    public CommentException(Throwable throwable) {
        super(throwable);
    }

    public CommentException(String msg) {
        super(msg);
    }
}