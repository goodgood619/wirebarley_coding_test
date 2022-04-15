package com.example.wirebarley_coding_test.exception;

import com.example.wirebarley_coding_test.util.ResponseCode;

public class ExchangeRateException extends CommonException {

  public enum ExchangeRateExceptionCode implements ResponseCode {
    NOT_AVAILABLE("ERE-01", "not available ExchangeRate API"),
    EMPTY("ERE-02", "exchange rate is Empty");

    private String code;
    private String message;

    ExchangeRateExceptionCode(String code, String message) {
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

  public ExchangeRateException(ResponseCode responseCode) {
    super(responseCode);
  }

}
