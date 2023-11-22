package com.kth.mssage.info.properties;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "weather")
public class WeatherProperties {

    private final String BASE_DATE_PATTERN = "yyyyMMdd";
    private final String BASE_MINUTE = "00";

    private final String serviceKey;
    private final String numOfRows;
    private final String pageNo;
    private final String dataType;

    public String getBaseDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(BASE_DATE_PATTERN));
    }

    public String getBaseTime() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        if (validRenewalWeatherInfo40LessMinute(now.getMinute())) {
            return hour - 1 + BASE_MINUTE;
        }
        return hour + BASE_MINUTE;
    }

    private boolean validRenewalWeatherInfo40LessMinute(int minute) {
        return minute < 40;
    }
}
