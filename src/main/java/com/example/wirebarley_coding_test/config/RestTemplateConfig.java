package com.example.wirebarley_coding_test.config;

import com.example.wirebarley_coding_test.advice.RestTemplateResponseErrorhandler;
import com.example.wirebarley_coding_test.interceptor.RestTemplateLoggingInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  private static final int TIME_OUT_LIMIT = 1800;
  private static final int CONNECT_TIME_OUT = 600;

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    CloseableHttpClient httpClient = HttpClientBuilder.create()
        .setMaxConnTotal(50)
        .setMaxConnPerRoute(20)
        .build();

    return restTemplateBuilder
        .requestFactory(() -> {
          HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
          factory.setHttpClient(httpClient);
          factory.setConnectionRequestTimeout(CONNECT_TIME_OUT);
          factory.setConnectTimeout(CONNECT_TIME_OUT);
          factory.setReadTimeout(TIME_OUT_LIMIT);
          return new BufferingClientHttpRequestFactory(factory);
        })
        .additionalInterceptors(new RestTemplateLoggingInterceptor())
        .errorHandler(new RestTemplateResponseErrorhandler())
        .build();
  }

}
