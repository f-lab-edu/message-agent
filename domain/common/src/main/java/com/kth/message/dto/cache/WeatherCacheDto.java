package com.kth.message.dto.cache;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Getter;

@Getter
public class WeatherCacheDto {

	private final String weatherData;
	private final LocalDateTime localDateTime;

	public WeatherCacheDto(String weatherData) {
		this.weatherData = weatherData;
		this.localDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
	}
}
