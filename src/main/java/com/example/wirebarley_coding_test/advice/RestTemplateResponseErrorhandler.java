package com.example.wirebarley_coding_test.advice;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestTemplateResponseErrorhandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    return (
        response.getStatusCode().series() == CLIENT_ERROR
            || response.getStatusCode().series() == SERVER_ERROR);
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    if (response.getStatusCode()
        .series() == SERVER_ERROR) {
      // handle SERVER_ERROR
      throw new IOException(response.getStatusText());
    } else if (response.getStatusCode()
        .series() == CLIENT_ERROR) {
      // handle CLIENT_ERROR
      if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new IOException(response.getStatusText());
      }
    }
  }
}
