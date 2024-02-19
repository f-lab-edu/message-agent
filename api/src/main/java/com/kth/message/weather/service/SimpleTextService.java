package com.kth.message.weather.service;

import com.kth.message.dto.weather.WeatherInfoDto;
import com.kth.message.dto.weather.request.RequestActionDto;
import com.kth.message.dto.weather.request.WeatherDto;
import com.kth.message.dto.weather.response.SimpleTextContentDto;
import com.kth.message.dto.weather.response.SimpleTextDto;
import com.kth.message.dto.weather.response.TemplateDto;
import com.kth.message.weather.service.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class SimpleTextService {

    private final WeatherService weatherService;

    public Mono<TemplateDto> createMessage(RequestActionDto requestActionDto) {
		WeatherDto weatherDto = requestActionDto.findWeatherLocation();
        return createWeatherMessage(weatherDto)
			.map(this::setUpTextMessage);
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

    private TemplateDto setUpTextMessage(SimpleTextContentDto textContent) {
        SimpleTextDto simpleTextDto = SimpleTextDto.builder()
                .simpleText(textContent)
                .build();

        TemplateDto templateDto = TemplateDto.builder().build();

        templateDto.addOutput(simpleTextDto);

        return templateDto;
    }
}
