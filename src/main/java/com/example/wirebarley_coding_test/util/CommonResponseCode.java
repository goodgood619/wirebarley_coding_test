package com.example.wirebarley_coding_test.util;

public enum CommonResponseCode implements ResponseCode {

  SUCCESS("CMN-001", "success"),
  FAIL("CMN-002", "fail");

  private String code;
  private String message;

  CommonResponseCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
