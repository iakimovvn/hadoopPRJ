package ru.yakimovvn.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Slf4j
@ControllerAdvice
public class WebExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception.class)
    public String handleProductNotFoundException(final Exception ex, Model model) {
        model.addAttribute("errorCode", 404);
        model.addAttribute("errorText", "NOT FOUND");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
