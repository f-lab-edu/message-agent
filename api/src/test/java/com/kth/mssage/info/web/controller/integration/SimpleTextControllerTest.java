package com.kth.mssage.info.web.controller.integration;

import com.kth.mssage.info.service.SimpleTextService;
import com.kth.mssage.info.web.controller.util.JsonReader;
import com.kth.mssage.info.web.dto.info.WeatherInfoDto;
import com.kth.mssage.info.web.dto.request.skill.WeatherDto;
import com.kth.mssage.info.web.dto.response.TemplateDto;
import com.kth.mssage.info.web.dto.response.skill.simpletext.SimpleTextContentDto;
import com.kth.mssage.info.web.dto.response.skill.simpletext.SimpleTextDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
public class SimpleTextControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SimpleTextService simpleTextService;

    @Test
    public void testTextCardController() throws Exception {
        WeatherDto weatherDto = WeatherDto.builder()
                .location("대전광역시 서구 둔산동")
                .build();

        WeatherInfoDto weatherInfoDto =
                new WeatherInfoDto("대전광역시 서구 둔산동", 10.0, 10.0, 10.0);

        SimpleTextContentDto simpleTextContentDto = SimpleTextContentDto.builder()
                .text("대전광역시 서구 둔산동 \n현재 온도: 10.0°C \n" +
                        "강수량: 10.0% \n습도: 10.0% \n현재 시간: " + weatherInfoDto.getLastUpdateTime())
                .build();

        when(simpleTextService.createWeatherMessage(weatherDto)).thenReturn(simpleTextContentDto);

        SimpleTextDto simpleTextDto = SimpleTextDto.builder()
                .simpleText(simpleTextContentDto)
                .build();

        TemplateDto<SimpleTextDto> templateDto = TemplateDto.<SimpleTextDto>builder()
                .build();

        templateDto.addOutput(simpleTextDto);

        when(simpleTextService.createMessage(any())).thenReturn(templateDto);

        String jsonRequest = JsonReader.readJsonFile("__files/payload/chatbot-weather-message-request.json");
        log.info("jsonRequest = {}", jsonRequest);

        mockMvc.perform(post("/simple-text/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.template.outputs[0].simpleText.text").isString());
    }

}
