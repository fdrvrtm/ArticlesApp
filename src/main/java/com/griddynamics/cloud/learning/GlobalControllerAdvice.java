package com.griddynamics.cloud.learning;

import com.griddynamics.cloud.learning.web.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ErrorResponse> handleBadRequest(Exception ex) {
        log.info(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
