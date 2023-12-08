package com.kth.mssage.info.task;

import com.kth.mssage.info.service.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WeatherScheduledTask implements InitializingBean {

    private final WeatherService weatherService;

    @Override
    public void afterPropertiesSet() {
        weatherService.loadWeatherInfo();
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    @CacheEvict(value = "weatherInfo", allEntries = true)
    public void updateWeatherInfoCSV() {
        weatherService.loadWeatherInfo();
    }

}
