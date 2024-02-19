package com.kth.message.dto.weather.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestActionDto {

    private final ActionDto action;

    @JsonCreator
    public RequestActionDto(@JsonProperty("action") ActionDto action) {
        this.action = action;
    }

    public WeatherDto findWeatherLocation() {
        return (WeatherDto) action.getParamDto();
    }
}
