package com.food.foodorderapi.library.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;



@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseMessageBuilder.ResponseMessage<ApiExceptionResponse> handleBusiness(
      BusinessException ex, HttpServletRequest req, HttpServletResponse resp) {

    resp.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
    ApiExceptionResponse response = new ApiExceptionResponse();
    response.setErrorCode(ex.getCode());
    response.setErrorMsg(ex.getMessage());
    response.setPath(req.getRequestURI());
    return new ResponseMessageBuilder<ApiExceptionResponse>()
        .failed(HttpStatus.BAD_REQUEST.value())
        .addData(response)
        .build();
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseMessageBuilder.ResponseMessage<ApiExceptionResponse> handleNotReadable(
      HttpMessageNotReadableException ex, HttpServletRequest req, HttpServletResponse resp) {

    resp.setStatus(HttpStatus.BAD_REQUEST.value());
    ApiExceptionResponse response = new ApiExceptionResponse();
    response.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
    response.setErrorMsg(ex.getMessage());
    response.setPath(req.getRequestURI());
    return new ResponseMessageBuilder<ApiExceptionResponse>()
        .failed(HttpStatus.BAD_REQUEST.value())
        .addData(response)
        .build();
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseMessageBuilder.ResponseMessage<ApiExceptionResponse> handleMissingParam(
      MissingServletRequestParameterException ex,
      HttpServletRequest req,
      HttpServletResponse resp) {

    resp.setStatus(HttpStatus.BAD_REQUEST.value());

    ApiExceptionResponse r = new ApiExceptionResponse();
    r.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
    r.setErrorMsg(ex.getMessage());
    r.setPath(req.getRequestURI());

    return new ResponseMessageBuilder<ApiExceptionResponse>()
        .failed(HttpStatus.BAD_REQUEST.value())
        .addData(r)
        .build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseMessageBuilder.ResponseMessage<ApiExceptionResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpServletRequest req, HttpServletResponse resp) {

    resp.setStatus(HttpStatus.BAD_REQUEST.value());
    String msg =
        Optional.ofNullable(ex.getBindingResult().getFieldError())
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .orElse("Validation failed");

    ApiExceptionResponse r = new ApiExceptionResponse();
    r.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
    r.setErrorMsg(msg);
    r.setPath(req.getRequestURI());

    return new ResponseMessageBuilder<ApiExceptionResponse>()
        .failed(HttpStatus.BAD_REQUEST.value())
        .addData(r)
        .build();
  }
}
