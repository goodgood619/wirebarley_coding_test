package com.example.wirebarley_coding_test.advice;

import com.example.wirebarley_coding_test.exception.CommonException;
import com.example.wirebarley_coding_test.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import com.example.wirebarley_coding_test.util.CommonResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

  @ResponseStatus(value = HttpStatus.OK)
  @ExceptionHandler(value = CommonException.class)
  public CommonResponse<?> defaultErrorHandler(HttpServletRequest req, CommonException e) {
    logger.warn("defaultErrorHandler : " + e.toString());
    return new CommonResponse<>(e.getResponseCode());
  }

  @ResponseStatus(value = HttpStatus.OK)
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public CommonResponse<?> validationErrorHandler(HttpServletRequest req,
      MethodArgumentNotValidException e) {
    logger.warn("validationErrorHandler : " + e.toString());
    return new CommonResponse<>(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
  }


}
