package com.erick.blog.exceptions;

public class AlbumException extends RuntimeException {

    public AlbumException(Throwable throwable) {
        super(throwable);
    }

    public AlbumException(String msg) {
        super(msg);
    }
}