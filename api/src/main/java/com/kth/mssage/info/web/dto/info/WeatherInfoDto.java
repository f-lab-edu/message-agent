package com.kth.mssage.info.web.dto.info;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class WeatherInfoDto {

    private final String location;
    private final double temp;
    private final double rainAmount;
    private final double humid;
    private final String lastUpdateTime;

    public WeatherInfoDto(String location, double temp, double rainAmount, double humid) {
        this.location = location;
        this.temp = temp;
        this.rainAmount = rainAmount;
        this.humid = humid;
        this.lastUpdateTime = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E) HH:mm")
        );
    }

    @Override
    public String toString() {
        return location +
                "현재 온도: " + temp + "\n" +
                "강수량: " + rainAmount + "% \n" +
                "습도: " + humid + "% \n" +
                "현재 시간" + lastUpdateTime;
    }
}
