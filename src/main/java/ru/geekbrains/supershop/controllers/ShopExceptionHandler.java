package ru.geekbrains.supershop.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.geekbrains.supershop.exceptions.ProductNotFoundException;
import ru.geekbrains.supershop.exceptions.UnsupportedMediaTypeException;
import ru.geekbrains.supershop.exceptions.WrongCaptchaCodeException;

@Slf4j
@ControllerAdvice
public class ShopExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFoundException(final ProductNotFoundException ex, Model model) {
        model.addAttribute("errorCode", 404);
        model.addAttribute("errorText", "NOT FOUND");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public String handleUnsupportedMediaTypeException(final UnsupportedMediaTypeException ex, Model model) {
        model.addAttribute("errorCode", 415);
        model.addAttribute("errorText", "UNSUPPORTED MEDIA TYPE");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongCaptchaCodeException.class)
    public String handleWrongCaptchaException(final WrongCaptchaCodeException ex, Model model) {
        model.addAttribute("errorCode", 400);
        model.addAttribute("errorText", "BAD REQUEST");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

}