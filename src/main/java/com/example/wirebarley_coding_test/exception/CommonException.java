package com.example.wirebarley_coding_test.exception;

import com.example.wirebarley_coding_test.util.ResponseCode;

public abstract class CommonException extends RuntimeException {

  private ResponseCode responseCode;

  public CommonException(ResponseCode responseCode) {
    super(responseCode.getMessage());
    this.responseCode = responseCode;
  }

  public CommonException(ResponseCode responseCode, String detailMessage) {
    super(responseCode.getMessage() + " : " + detailMessage);
    this.responseCode = responseCode;
  }

  public ResponseCode getResponseCode() {
    return responseCode;
  }

}
