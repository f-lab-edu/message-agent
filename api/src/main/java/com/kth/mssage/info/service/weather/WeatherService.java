package com.kth.mssage.info.service.weather;

import com.kth.mssage.info.client.WeatherApiClient;
import com.kth.mssage.info.properties.WeatherProperties;
import com.kth.mssage.info.web.dto.info.WeatherInfoDto;
import com.kth.mssage.info.web.dto.request.skill.WeatherDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {

    private final WeatherProperties weatherProperties;
    private final WeatherApiClient weatherApiClient;

    public WeatherInfoDto createWeatherInfoDto(WeatherDto weatherDto) {
        // weatherDto의 location으로 격자(위도,경도가 아님) 찾아와야한다
        // 아래는 예시 데이터
        String location = weatherDto.getLocation();
        String nx = "67";
        String ny = "100";
        String weatherInfo = getWeatherInfo(nx, ny);
        log.debug("데이터를 가지고 오나요? "+ weatherInfo);

        JSONObject jsonObject = new JSONObject(weatherInfo);
        JSONArray jsonArray = jsonObject.getJSONObject("response")
                .getJSONObject("body")
                .getJSONObject("items")
                .getJSONArray("item");

        double temp = 0.0;
        double rainAmount = 0.0;
        double humid = 0.0;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject weatherObject = jsonArray.getJSONObject(i);
            String category = weatherObject.getString("category");
            double obsrValue = weatherObject.getDouble("obsrValue");

            switch (category) {
                case "T1H" -> temp = obsrValue;
                case "RN1" -> rainAmount = obsrValue;
                case "REH" -> humid = obsrValue;
            }
        }

        return new WeatherInfoDto(location, temp, rainAmount, humid);
    }

    private String getWeatherInfo(String nx, String ny) {
        return weatherApiClient.getWeatherInfo(weatherProperties.getServiceKey(),
                weatherProperties.getNumOfRows(),
                weatherProperties.getPageNo(),
                weatherProperties.getDataType(),
                weatherProperties.getBaseDate(),
                weatherProperties.getBaseTime(),
                nx,
                ny);
    }

}
