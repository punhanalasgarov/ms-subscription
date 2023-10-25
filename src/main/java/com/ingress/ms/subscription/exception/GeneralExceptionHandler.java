package com.ingress.ms.subscription.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.ingress.ms.subscription.model.enums.ErrorCode.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception e){
        log.error("Exception ", e);
        return new ErrorResponse(UNEXPECTED_ERROR.getCode(), UNEXPECTED_ERROR.getDescription());
    }

}
