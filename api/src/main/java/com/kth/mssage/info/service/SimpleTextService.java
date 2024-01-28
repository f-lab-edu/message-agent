package com.kth.mssage.info.service;

import com.kth.mssage.info.service.weather.WeatherService;
import com.kth.mssage.info.web.dto.info.WeatherInfoDto;
import com.kth.mssage.info.web.dto.request.ParamDto;
import com.kth.mssage.info.web.dto.request.RequestActionDto;
import com.kth.mssage.info.web.dto.request.skill.WeatherDto;
import com.kth.mssage.info.web.dto.response.TemplateDto;
import com.kth.mssage.info.web.dto.response.skill.simpletext.SimpleTextContentDto;
import com.kth.mssage.info.web.dto.response.skill.simpletext.SimpleTextDto;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class SimpleTextService {

    private final WeatherService weatherService;

    public Mono<TemplateDto<SimpleTextDto>> createMessage(RequestActionDto<ParamDto> requestActionDto) {
        return checkMessageType(requestActionDto);
    }

    private Mono<TemplateDto<SimpleTextDto>> checkMessageType(RequestActionDto<ParamDto> requestActionDto) {
		if (requestActionDto.findTypeParamDto() instanceof WeatherDto weatherDto) {
			return createWeatherMessage(weatherDto)
				.map(this::setUpTextMessage);
		}

		//TODO: 임시 값 지정
		return Mono.empty();
    }

    public Mono<SimpleTextContentDto> createWeatherMessage(WeatherDto weatherDto) {
        return weatherService.getWeatherInfoDto(weatherDto)
			.map(this::createWeatherTextString)
			.map(text -> SimpleTextContentDto.builder()
				.text(text)
				.build()
			);
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
