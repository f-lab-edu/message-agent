package com.kth.mssage.info.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weather-client",
        url = "${weather.api}"
)
public interface WeatherApiClient {

    @GetMapping(path = "/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst")
    String getWeatherInfo(@RequestParam(value = "serviceKey") String serviceKey,
                          @RequestParam(value = "numOfRows") String numOfRows,
                          @RequestParam(value = "pageNo") String pageNo,
                          @RequestParam(value = "dataType") String dataType,
                          @RequestParam(value = "base_date") String baseDate,
                          @RequestParam(value = "base_time") String baseTime,
                          @RequestParam(value = "nx") String nx,
                          @RequestParam(value = "ny") String ny);

}
