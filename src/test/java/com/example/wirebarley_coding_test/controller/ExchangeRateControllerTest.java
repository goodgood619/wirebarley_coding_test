package com.example.wirebarley_coding_test.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(ExchangeRateController.class) // ExchangeRateController ??? ????????????, ???????????? ????????? ????????? ???????????????
//@SpringBootTest // ?????? Bean ?????????????????? , ?????? Autowired ???????????? ??? ?????? ??????
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

  private RestDocumentationResultHandler document; // ?????? directory(target/generated-sources)???
  private final String DOCUMENT_IDENTIFIER = "{class-name}/{method-name}"; // class-name??? method-name?????? ???????????????

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
        .perform(get(URI_TEMPLATE)
            .param("fromSend", "usd")
            .param("toSend", "Krw")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document(DOCUMENT_IDENTIFIER, requestParameters(
            parameterWithName("fromSend").description("?????? ??? ?????? ??????"),
            parameterWithName("toSend").description("?????? ??? ?????? ??????")
            ), responseFields(
            subsectionWithPath("data").type(JsonFieldType.OBJECT).description("?????????").optional(),
            fieldWithPath("code").type(JsonFieldType.STRING).description("?????? ??????"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????????"))
        ));

  }

  @Test
  void getReceptionMoneyTest() throws Exception {
    ReceptionMoneyDTO dto = new ReceptionMoneyDTO("usd", "Krw", 100);
    /*
    // ????????? ????????? ???????????? ?????????, mockBean?????? ?????????????????? ?????? ?????? ??????
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
            fieldWithPath("fromSend").description("?????? ??? ?????? ??????"),
            fieldWithPath("toSend").description("?????? ??? ?????? ??????"),
            fieldWithPath("receptionMoney").description("?????????")
            ), responseFields(
            subsectionWithPath("data").type(JsonFieldType.OBJECT).description("?????????").optional(),
            fieldWithPath("code").type(JsonFieldType.STRING).description("?????? ??????"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????????"))
        ));

  }
}
