package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/403");
        modelAndView.addObject("errorMessage", "Access denied for this resource!");
        return modelAndView;
    }

}