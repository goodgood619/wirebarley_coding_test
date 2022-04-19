package com.example.wirebarley_coding_test.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.wirebarley_coding_test.batch.BatchService;
import com.example.wirebarley_coding_test.model.ReceptionMoneyDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(ExchangeRateController.class)
@Import(ExchangeRateController.class)
@AutoConfigureRestDocs
public class ExchangeRateControllerTest {

  @MockBean
  private Map<String, Double> currentExchangeList;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private BatchService batchService;

  private final String URI_TEMPLATE = "/";
  private MockMvc mockMvc;

  private RestDocumentationResultHandler document;
  private final String DOCUMENT_IDENTIFIER = "{class-name}/{method-name}";

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentationContextProvider) {

    this.document = document(DOCUMENT_IDENTIFIER, preprocessResponse(prettyPrint()));
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(
            documentationConfiguration(restDocumentationContextProvider)
                .operationPreprocessors()
                .withRequestDefaults(modifyUris().host("localhost").removePort(),
                    prettyPrint())
                .withResponseDefaults(prettyPrint())
        )
        .alwaysDo(document)
        .build();

  }


  @Test
  void getExchangeRateTest() throws Exception {

    this.mockMvc
        .perform(RestDocumentationRequestBuilders.get(URI_TEMPLATE + "?fromSend=usd&toSend=Krw")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document(DOCUMENT_IDENTIFIER, requestParameters(
            parameterWithName("fromSend").description("수취 전 통화 타입"),
            parameterWithName("toSend").description("수취 후 통화 타입")
            ), responseFields(
            fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터").optional(),
            fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("응답 코드 상세 메세지"))
        ));

  }

  @Test
  void getReceptionMoneyTest() throws Exception {
    ReceptionMoneyDTO dto = new ReceptionMoneyDTO("usd", "Krw", 100);
    /*
    // 이벤트 호출로 불러오기 때문에, mockBean으로 주입받았기에 항상 비어 있다
    this.currentExchangeList.put("usdKrw",1000.00);
    this.currentExchangeList.put("usdJpy",100.00);
    this.currentExchangeList.put("usdPhp",1122.00);
    */

    this.mockMvc
        .perform(post(URI_TEMPLATE)
            .content(objectMapper.writeValueAsString(dto))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(document(DOCUMENT_IDENTIFIER, requestFields(
            fieldWithPath("fromSend").description("수취 전 통화 타입"),
            fieldWithPath("toSend").description("수취 후 통화 타입"),
            fieldWithPath("receptionMoney").description("송금액")
            ), responseFields(
            fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터").optional(),
            fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("응답 코드 상세 메세지"))
        ));

  }
}
