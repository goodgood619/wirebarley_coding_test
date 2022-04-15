package com.example.wirebarley_coding_test.interceptor;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

  private final Logger logger = LoggerFactory.getLogger(RestTemplateLoggingInterceptor.class);

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    URI uri = request.getURI();

    loggingRequest(request, body);
    ClientHttpResponse response = execution.execute(request, body);
    loggingResponse(response, uri);

    return response;
  }

  private void loggingRequest(HttpRequest request, byte[] body) {
    logger.info("Request Uri: {}, Method: {}, Request Body: {}",
        request.getURI(),
        request.getMethod(),
        new String(body, StandardCharsets.UTF_8)
    );
  }

  private void loggingResponse(ClientHttpResponse response, URI uri) throws IOException {
    logger.info("Response Uri: {}, Status Code: {}, Response Body: {}",
        uri,
        response.getStatusCode(),
        StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8)
    );
  }

}
