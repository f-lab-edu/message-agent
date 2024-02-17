package com.kth.message.info.web.controller.integration;

import com.kth.message.dto.weather.WeatherInfoDto;
import com.kth.message.dto.weather.request.WeatherDto;
import com.kth.message.dto.weather.response.SimpleTextContentDto;
import com.kth.message.dto.weather.response.SimpleTextDto;
import com.kth.message.dto.weather.response.TemplateDto;
import com.kth.message.weather.service.SimpleTextService;
import com.kth.message.info.web.controller.util.JsonReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@AutoConfigureMockMvc
@ActiveProfiles("test")
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

        when(simpleTextService.createWeatherMessage(weatherDto)).thenReturn(
            Mono.just(simpleTextContentDto));

        SimpleTextDto simpleTextDto = SimpleTextDto.builder()
                .simpleText(simpleTextContentDto)
                .build();

        TemplateDto templateDto = TemplateDto.builder()
                .build();

        templateDto.addOutput(simpleTextDto);

        when(simpleTextService.createMessage(any())).thenReturn(
            Mono.just(templateDto));

        String jsonRequest = JsonReader.readJsonFile("__files/payload/chatbot-weather-message-request.json");
        log.info("jsonRequest = {}", jsonRequest);

        //todo:
//        mockMvc.perform(post("/simple-text/message")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.template.outputs[0].simpleText.text").isString());
    }

}
