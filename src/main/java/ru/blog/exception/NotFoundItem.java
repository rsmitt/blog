package ru.blog.exception;

public class NotFoundItem extends RuntimeException{

    public NotFoundItem() {
    }

    public NotFoundItem(String message) {
        super(message);
    }

    public NotFoundItem(String message, Throwable cause) {
        super(message, cause);
    }
}
