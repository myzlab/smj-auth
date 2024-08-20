package com.smartjob.auth.controllers;

import com.myzlab.k.DynamicObject;
import com.myzlab.k.KException;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CommonController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(KException.class)
    public ResponseEntity handleCustomException(KException kException) {
        return ResponseEntity.status(kException.getStatus()).body(
            DynamicObject.create()
                .add("timestamp", LocalDateTime.now())
                .add("error", kException.getStatus().name())
                .add("status", kException.getStatus().value())
                .add("message", kException.getMessage())
            .toMap()
        );
    }

}