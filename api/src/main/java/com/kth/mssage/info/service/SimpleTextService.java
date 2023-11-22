package com.kth.mssage.info.service;

import com.kth.mssage.info.service.weather.WeatherService;
import com.kth.mssage.info.web.dto.request.skill.WeatherDto;
import com.kth.mssage.info.web.dto.response.TemplateDto;
import com.kth.mssage.info.web.dto.response.skill.simpletext.SimpleTextContentDto;
import com.kth.mssage.info.web.dto.response.skill.simpletext.SimpleTextDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SimpleTextService {

    private final WeatherService weatherService;

    private TemplateDto<SimpleTextDto> setUpTextMessage(String textMessage) {
        SimpleTextContentDto textDto = SimpleTextContentDto.builder()
                .text(textMessage)
                .build();

        SimpleTextDto simpleTextDto = SimpleTextDto.builder()
                .simpleText(textDto)
                .build();

        TemplateDto<SimpleTextDto> templateDto = TemplateDto.<SimpleTextDto>builder()
                .build();

        templateDto.addOutput(simpleTextDto);

        return templateDto;
    }

    public TemplateDto<SimpleTextDto> createWeatherMessage(WeatherDto weatherDto) {
        return setUpTextMessage(weatherService.createWeatherInfoDto(weatherDto).toString());
    }

}
