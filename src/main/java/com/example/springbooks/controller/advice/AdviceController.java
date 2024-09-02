package com.example.springbooks.controller.advice;

import com.example.springbooks.controller.exception.CustomException;
import com.example.springbooks.service.ExceptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AdviceController {
    private final ExceptionService service;


    @ExceptionHandler
    public ResponseEntity<String> handle(CustomException e){
        return new ResponseEntity<>( e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
