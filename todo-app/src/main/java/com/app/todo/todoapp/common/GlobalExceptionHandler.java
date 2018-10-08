package com.app.todo.todoapp.common;

import com.app.todo.todoapp.common.bean.GenericResponseBean;
import com.app.todo.todoapp.common.exception.ApplicationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public GenericResponseBean handleApplicationException(ApplicationException ex, HttpServletResponse response){
        GenericResponseBean genericResponseBean = GenericResponseBean.build(ex.getMessage());
        genericResponseBean.setError(true);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return genericResponseBean;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public GenericResponseBean handleBadCredentialsException(BadCredentialsException ex, HttpServletResponse response){
        GenericResponseBean genericResponseBean = GenericResponseBean.build(MessageKeys.BAD_CREDENTIALS);
        genericResponseBean.setError(true);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return genericResponseBean;
    }

    @ExceptionHandler(AuthenticationException.class)
    public GenericResponseBean handleAuthenticationException(AuthenticationException ex, HttpServletResponse response){
        GenericResponseBean genericResponseBean = GenericResponseBean.build(MessageKeys.UNAUTHORIZED);
        genericResponseBean.setError(true);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return genericResponseBean;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        GenericResponseBean genericResponseBean = GenericResponseBean.build(MessageKeys.UNAUTHORIZED);
        genericResponseBean.setError(true);
        genericResponseBean.setData(ex.getBindingResult());
        return ResponseEntity.badRequest().body(genericResponseBean);
    }
}