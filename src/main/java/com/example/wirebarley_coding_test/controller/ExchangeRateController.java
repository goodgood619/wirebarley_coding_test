package com.example.wirebarley_coding_test.controller;

import com.example.wirebarley_coding_test.model.ReceptionMoneyDTO;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRateController {

  private final Map<String, Double> currentExchangeList;

  public ExchangeRateController(
      Map<String, Double> currentExchangeList) {
    this.currentExchangeList = currentExchangeList;
  }

  // 환율 정보 반환
  @GetMapping("")
  public String getExchangeRate() {
    return "test";
  }

  // 수취 금액 반환
  @PostMapping
  public Double getReceptionMoney(@Valid @RequestBody ReceptionMoneyDTO receptionMoneyDTO) {
    StringBuilder sb = new StringBuilder();
    String fromSend = receptionMoneyDTO.getFromSend();
    String toSend = receptionMoneyDTO.getToSend();
    sb.append(fromSend).append(toSend);
    return currentExchangeList.get(sb.toString()) * receptionMoneyDTO.getReceptionMoney();
  }

}
