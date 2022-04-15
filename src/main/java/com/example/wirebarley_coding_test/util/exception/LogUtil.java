package com.example.wirebarley_coding_test.util.exception;

import com.example.wirebarley_coding_test.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

  public static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

  private static final String CHECKED_EXCEPTION_CODE = "CHECKED";
  private static final String ERROR_LOG_FORMAT = "[{}] Exception occurred.";

  public static void error(Exception e) {
    logger.error(ERROR_LOG_FORMAT, getExceptionCode(e), e);
  }

  private static String getExceptionCode(Exception e) {
    return (e instanceof CommonException)
        ? ((CommonException) e).getResponseCode().getCode()
        : CHECKED_EXCEPTION_CODE;
  }

}
