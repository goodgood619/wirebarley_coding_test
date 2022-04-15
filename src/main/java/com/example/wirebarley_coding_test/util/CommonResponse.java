package com.example.wirebarley_coding_test.util;

public class CommonResponse<T> {

  private final T data;
  private final String code;
  private final String message;

  public CommonResponse(ResponseCode responseCode) {
    this(null, responseCode);
  }

  public CommonResponse(T data, ResponseCode responseCode) {
    this.data = data;
    this.code = responseCode.getCode();
    this.message = responseCode.getMessage();
  }

  public T getData() {
    return data;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}
