package com.erick.blog.exceptions;

public class DeleteException extends Exception {
    public DeleteException() {
        super("Only the creator can delete this comment");
    }
}