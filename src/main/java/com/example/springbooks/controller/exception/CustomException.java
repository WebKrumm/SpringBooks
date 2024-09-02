package com.example.springbooks.controller.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class CustomException extends ResponseStatusException {
    public CustomException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
