package com.food.foodorderapi.library.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class BusinessException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  private final String code;

  public BusinessException(String code, String message) {
    super(message);
    this.code = code;
  }

  public BusinessException(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  @Override
  public String toString() {
    return getClass().getName() + ": [" + code + "] " + getMessage();
  }
}
