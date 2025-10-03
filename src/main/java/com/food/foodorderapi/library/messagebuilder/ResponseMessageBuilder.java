package com.food.foodorderapi.library.messagebuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.slf4j.MDC;

import com.food.foodorderapi.library.constant.Constant;




@Data
public class ResponseMessageBuilder<T> {

  private boolean success = false;
  private T data;
  private int status;
  private String traceId;

  public ResponseMessageBuilder<T> success() {
    this.success = true;
    this.status = 200;
    return this;
  }

  public ResponseMessageBuilder<T> success(int status) {
    this.success = true;
    this.status = status;
    return this;
  }

  public ResponseMessageBuilder<T> failed() {
    this.success = false;
    this.status = 400;
    return this;
  }

  public ResponseMessageBuilder<T> failed(int status) {
    this.success = false;
    this.status = status;
    return this;
  }

  public ResponseMessageBuilder<T> addData(T data) {
    this.data = data;
    return this;
  }

  public ResponseMessageBuilder<T> message(String message) {
    return this;
  }

  public ResponseMessageBuilder<T> status(int status) {
    this.status = status;
    return this;
  }

  public ResponseMessage<T> build() {
    this.traceId = MDC.get(Constant.TRACE_ID);
    return new ResponseMessage<>(success, status, data, traceId);
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ResponseMessage<T> {
    private boolean success;
    private int status;
    private T data;
    private String traceId;
  }
}
