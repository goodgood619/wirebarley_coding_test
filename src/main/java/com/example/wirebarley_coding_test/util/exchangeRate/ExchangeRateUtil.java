package com.example.wirebarley_coding_test.util.exchangeRate;

import com.example.wirebarley_coding_test.exception.ExchangeRateException;
import com.example.wirebarley_coding_test.exception.ExchangeRateException.ExchangeRateExceptionCode;
import com.example.wirebarley_coding_test.model.ExchangeFromUSD;
import com.example.wirebarley_coding_test.model.ExchangeRateVO;
import com.example.wirebarley_coding_test.util.exception.LogUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ExchangeRateUtil {

  private final Logger logger = LoggerFactory.getLogger(ExchangeRateUtil.class);

  @Value("${exchangeRate.key}")
  String accessKey;

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  @Resource
  private Map<String, Double> currentExchangeList;

  private final String exchangeRateEndpoint = "http://api.currencylayer.com";
  private final String livePrefix = "/live";
  private final String historicalPrefix = "/historical";
  private final String convertPrefix = "/convert";
  private final String timeframePrefix = "/timeframe";
  private final String changePrefix = "/change";

  public ExchangeRateUtil(RestTemplate restTemplate,
      ObjectMapper objectMapper) {
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
  }

  public boolean getExchangeRate() throws ExchangeRateException {

    String test = EarnExchangeRate(livePrefix);
    return test.equals("success");
  }

  private String EarnExchangeRate(String prefix) throws ExchangeRateException {
    String result = null;
    try {
      UriComponentsBuilder builder = UriComponentsBuilder
          .fromUriString(exchangeRateEndpoint + prefix)
          .queryParam("access_key", accessKey);

      ResponseEntity<String> response = restTemplate
          .exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(null), String.class);

      ExchangeRateVO exchangeRateVO = objectMapper
          .readValue(response.getBody(), ExchangeRateVO.class);

      ExchangeFromUSD quotes = exchangeRateVO.getQuotes();

      makeCurrentExchangeRateList(quotes);
    } catch (Exception e) {
      LogUtil.error(e);
      throw new ExchangeRateException(ExchangeRateExceptionCode.NOT_AVAILABLE);
    }
    result = "success";
    return result;
  }

  private void makeCurrentExchangeRateList(ExchangeFromUSD quotes) {

    currentExchangeList.put("usdKrw", quotes.getUsdKrw());
    currentExchangeList.put("usdJpy", quotes.getUsdJpy());
    currentExchangeList.put("usdPhp", quotes.getUsdPhp());

  }

}
