package com.example.springbooks.service;

import com.example.springbooks.controller.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ExceptionService {

public void methodThrowsException() {
    throw new CustomException(HttpStatus.NOT_FOUND, "Книга не найдена");
}



}
