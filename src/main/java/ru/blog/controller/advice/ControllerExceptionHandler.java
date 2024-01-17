package ru.blog.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.blog.exception.NotFoundItem;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundItem.class)
    public String page404(Exception ex) {
        return "error/404";
    }
}
