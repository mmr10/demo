package com.example.web;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserExceptionHandler
{


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorDTO processAccessDeniedExcpetion(AccessDeniedException e) {
        return new ErrorDTO("You are not allowed to access ", e.getMessage());
    }

    @ExceptionHandler(ClientAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorDTO processUnauthorizedExcpetion(AccessDeniedException e) {
        return new ErrorDTO("You are not authenticated  ", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> processRuntimeException(Exception ex) throws Exception {
        ResponseEntity.BodyBuilder builder;
        ErrorDTO errorDTO;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            errorDTO = new ErrorDTO("error." + responseStatus.value().value(), responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            errorDTO = new ErrorDTO("internel error", "Internal server error");
        }
        return builder.body(errorDTO);
    }
}
