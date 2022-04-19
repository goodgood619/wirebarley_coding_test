package com.example.wirebarley_coding_test.batch;

import com.example.wirebarley_coding_test.exception.ExchangeRateException;
import com.example.wirebarley_coding_test.util.exchangeRate.ExchangeRateUtil;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

  @Value("${livePrefix}")
  String livePrefix;

  @Value("${historicalPrefix}")
  String historicalPrefix;

  @Value("${convertPrefix}")
  String convertPrefix;

  @Value("${timeframePrefix}")
  String timeframePrefix;

  @Value("${changePrefix}")
  String changePrefix;

  private final ExchangeRateUtil exchangeRateUtil;
  private final Map<String, Double> currentExchangeList;

  private final Logger logger = LoggerFactory.getLogger(BatchService.class);

  public BatchService(ExchangeRateUtil exchangeRateUtil,
      Map<String, Double> currentExchangeList) {
    this.exchangeRateUtil = exchangeRateUtil;
    this.currentExchangeList = currentExchangeList;
  }

  public void batchCheckExchangeRate() throws ExchangeRateException {

    boolean result = exchangeRateUtil.getExchangeRate(livePrefix);

    logger.info(currentExchangeList.toString());
  }

}
