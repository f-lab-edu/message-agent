package com.kth.mssage.info.service;

import com.kth.mssage.info.service.weather.WeatherService;
import com.kth.mssage.info.web.dto.info.WeatherInfoDto;
import com.kth.mssage.info.web.dto.request.skill.WeatherDto;
import com.kth.mssage.info.web.dto.response.skill.simpletext.SimpleTextContentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
class SimpleTextServiceTest {

    @Mock
    WeatherService weatherService;
    SimpleTextService simpleTextService;

    @BeforeEach
    void setUp() {
        simpleTextService = new SimpleTextService(weatherService);
    }

    @Test
    void createWeatherMessage() {
        WeatherDto weatherDto = WeatherDto.builder()
                .location("대전광역시 서구 둔산동")
                .build();

        WeatherInfoDto weatherInfoDto =
                new WeatherInfoDto("대전광역시 서구 둔산동", 10.0, 10.0, 10.0);

        when(weatherService.createWeatherInfoDto(weatherDto)).thenReturn(weatherInfoDto);

        SimpleTextContentDto weatherMessage = simpleTextService.createWeatherMessage(weatherDto);
        String weatherMessageText = weatherMessage.getText();

        Assertions.assertEquals(weatherMessageText, "대전광역시 서구 둔산동 \n현재 온도: 10.0°C \n" +
                "강수량: 10.0% \n습도: 10.0% \n현재 시간: "+ weatherInfoDto.getLastUpdateTime());
    }
}
