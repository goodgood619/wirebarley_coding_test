package com.example.wirebarley_coding_test.model;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ReceptionMoneyDTO {

  @NotNull
  private String fromSend;

  @NotNull
  private String toSend;

  @NotNull(message = "송금액이 바르지 않습니다")
  @Min(value = 1, message = "송금액이 바르지 않습니다")
  @Max(value = 10000, message = "송금액이 바르지 않습니다")
  @Positive
  private Integer receptionMoney;

  public String getFromSend() {
    return fromSend;
  }

  public String getToSend() {
    return toSend;
  }

  public Integer getReceptionMoney() {
    return receptionMoney;
  }
}
