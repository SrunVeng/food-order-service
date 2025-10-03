package com.food.foodorderapi.library.exception;

import lombok.Data;

@Data
public class ApiExceptionResponse {

  private String errorCode;
  private String errorMsg;
  private String path;
}
