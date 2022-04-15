package com.example.wirebarley_coding_test.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrentExchangeRateConfig {

  @Bean
  public Map<String, Double> currentExchangeList() {
    return new HashMap<>();
  }
}
