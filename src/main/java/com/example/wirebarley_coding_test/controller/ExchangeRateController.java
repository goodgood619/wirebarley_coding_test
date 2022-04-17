package com.example.wirebarley_coding_test.controller;

import com.example.wirebarley_coding_test.exception.ExchangeRateException;
import com.example.wirebarley_coding_test.exception.ExchangeRateException.ExchangeRateExceptionCode;
import com.example.wirebarley_coding_test.exception.InvalidInputValueException;
import com.example.wirebarley_coding_test.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import com.example.wirebarley_coding_test.model.ReceptionMoneyDTO;
import com.example.wirebarley_coding_test.util.CommonResponse;
import com.example.wirebarley_coding_test.util.CommonResponseCode;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRateController {

  @Resource
  private Map<String, Double> currentExchangeList;

  // 환율 정보 반환
  @GetMapping("")
  public CommonResponse getExchangeRate() {
    if (currentExchangeList.isEmpty()) {
      throw new ExchangeRateException(ExchangeRateExceptionCode.EMPTY);
    }

    return new CommonResponse<>(currentExchangeList, CommonResponseCode.SUCCESS);
  }

  // 수취 금액 반환
  @PostMapping
  public CommonResponse getReceptionMoney(@Valid @RequestBody ReceptionMoneyDTO receptionMoneyDTO) {
    StringBuilder sb = new StringBuilder();
    String fromSend = receptionMoneyDTO.getFromSend();
    String toSend = receptionMoneyDTO.getToSend();
    sb.append(fromSend).append(toSend);

    if (currentExchangeList.get(sb.toString()) == null) {
      throw new InvalidInputValueException(
          InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
    }

    return new CommonResponse<>(
        currentExchangeList.get(sb.toString()) * receptionMoneyDTO.getReceptionMoney(),
        CommonResponseCode.SUCCESS);
  }

}
