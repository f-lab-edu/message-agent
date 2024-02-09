package com.kth.message.info.service;

import com.kth.message.weather.service.SimpleTextService;
import com.kth.message.weather.service.weather.WeatherService;
import com.kth.message.weather.web.dto.info.WeatherInfoDto;
import com.kth.message.weather.web.dto.request.skill.WeatherDto;
import com.kth.message.weather.web.dto.response.skill.simpletext.SimpleTextContentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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

        when(weatherService.getWeatherInfoDto(weatherDto)).thenReturn(
            Mono.just(weatherInfoDto));

        SimpleTextContentDto weatherMessage = simpleTextService.createWeatherMessage(weatherDto).block();
        String weatherMessageText = weatherMessage.getText();

        Assertions.assertEquals(weatherMessageText, "대전광역시 서구 둔산동 \n현재 온도: 10.0°C \n" +
                "강수량: 10.0% \n습도: 10.0% \n현재 시간: "+ weatherInfoDto.getLastUpdateTime());
    }
}
