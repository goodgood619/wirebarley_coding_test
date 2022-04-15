package com.example.wirebarley_coding_test.model;

public class ExchangeRateVO {

  private boolean success;
  private String terms;
  private String privacy;
  private String timeStamp;
  private String source;
  private ExchangeFromUSD quotes;

  public ExchangeFromUSD getQuotes() {
    return quotes;
  }
}
