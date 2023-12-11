package com.kth.mssage.info.service;

import com.kth.mssage.info.service.weather.WeatherService;
import com.kth.mssage.info.web.dto.info.WeatherInfoDto;
import com.kth.mssage.info.web.dto.request.ParamDto;
import com.kth.mssage.info.web.dto.request.RequestActionDto;
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

    public TemplateDto<SimpleTextDto> createMessage(RequestActionDto<ParamDto> requestActionDto) {
        return checkMessageType(requestActionDto);
    }

    private TemplateDto<SimpleTextDto> checkMessageType(RequestActionDto<ParamDto> requestActionDto) {
        if (requestActionDto.findTypeParamDto() instanceof WeatherDto weatherDto) {
            SimpleTextContentDto weatherMessage = createWeatherMessage(weatherDto);
            return setUpTextMessage(weatherMessage);
        }

        //TODO: 임시 값 지정
        return null;
    }

    public SimpleTextContentDto createWeatherMessage(WeatherDto weatherDto) {
        WeatherInfoDto weatherInfoDto = weatherService.getWeatherInfoDto(weatherDto);

        return SimpleTextContentDto.builder()
                .text(createWeatherTextString(weatherInfoDto))
                .build();
    }

    private String createWeatherTextString(WeatherInfoDto weatherInfoDto) {
        return weatherInfoDto.getLocation() + " \n" +
                "현재 온도: " + weatherInfoDto.getTemp() + "°C \n" +
                "강수량: " + weatherInfoDto.getRainAmount() + "% \n" +
                "습도: " + weatherInfoDto.getHumid() + "% \n" +
                "현재 시간: " + weatherInfoDto.getLastUpdateTime();
    }

    private TemplateDto<SimpleTextDto> setUpTextMessage(SimpleTextContentDto textContent) {
        SimpleTextDto simpleTextDto = SimpleTextDto.builder()
                .simpleText(textContent)
                .build();

        TemplateDto<SimpleTextDto> templateDto = TemplateDto.<SimpleTextDto>builder()
                .build();

        templateDto.addOutput(simpleTextDto);

        return templateDto;
    }
}
