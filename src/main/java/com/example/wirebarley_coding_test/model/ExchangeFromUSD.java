package com.example.wirebarley_coding_test.model;

import com.fasterxml.jackson.annotation.JsonProperty;

// 고민사항 : 이것이 점점 늘면 어떻게 처리를 해야할까... 분명 좋은 방법이 있는거같다
public class ExchangeFromUSD {

  @JsonProperty("USDKRW")
  private Double usdKrw;
  @JsonProperty("USDJPY")
  private Double usdJpy;
  @JsonProperty("USDPHP")
  private Double usdPhp;

  public Double getUsdKrw() {
    return usdKrw;
  }

  public Double getUsdJpy() {
    return usdJpy;
  }

  public Double getUsdPhp() {
    return usdPhp;
  }
}
