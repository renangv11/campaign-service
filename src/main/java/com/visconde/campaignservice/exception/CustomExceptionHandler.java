package com.visconde.campaignservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

public class CustomExceptionHandler {
    @ExceptionHandler(CampaignNotFoundException.class)
    public ResponseEntity<String> handlerAlreadyRegisteredMember(CampaignNotFoundException exception){
        return ResponseEntity.status(BAD_REQUEST).body(exception.getMessage());
    }
}
