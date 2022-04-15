package com.example.wirebarley_coding_test.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ObjectMapperConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return Jackson2ObjectMapperBuilder
        .json()
        .visibility(PropertyAccessor.ALL, Visibility.NONE)
        .visibility(PropertyAccessor.FIELD, Visibility.ANY)
        .build();
  }

}
