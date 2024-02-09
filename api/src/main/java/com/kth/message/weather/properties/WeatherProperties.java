package com.kth.message.weather.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "weather")
public class WeatherProperties {

    private final String serviceKey;
    private final String numOfRows;
    private final String pageNo;
    private final String dataType;

}
