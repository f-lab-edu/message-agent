package com.kth.mssage.info.service.weather;

import com.kth.mssage.info.client.WeatherApiClient;
import com.kth.mssage.info.properties.WeatherProperties;
import com.kth.mssage.info.web.dto.info.WeatherInfoDto;
import com.kth.mssage.info.web.dto.request.skill.WeatherDto;
import com.kth.mssage.repository.local.Geometry;
import com.kth.mssage.repository.local.GeometryRepository;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {

    private final static String BASE_DATE_PATTERN = "yyyyMMdd";
    private final static String BASE_TIME = "HH'00'";

    private final WeatherProperties weatherProperties;
    private final WeatherApiClient weatherApiClient;
    private final GeometryRepository geometryRepository;

    public WeatherInfoDto getWeatherInfoDto(WeatherDto weatherDto) {
        String location = weatherDto.getLocation();

        Geometry geometry = findByGeometryByLocation(location);

        String weatherInfo = getWeatherInfo(geometry.getNx(), geometry.getNy());
        log.info("날씨 Json data "+ weatherInfo);

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

    public Geometry findByGeometryByLocation(String location) {
        String[] parts = location.split(" ", 3);

        String regionCity = parts.length > 0 ? parts[0] : "";
        String regionTown = parts.length > 1  ? parts[1] : "";
        String regionVillage = parts.length > 2 ? parts[2] : "";


        return geometryRepository.findByRegionCityAndRegionTownAndRegionVillage(regionCity, regionTown, regionVillage);
    }

    private String getWeatherInfo(String nx, String ny) {
        return weatherApiClient.getWeatherInfo(
                weatherProperties.getServiceKey(),
                weatherProperties.getNumOfRows(),
                weatherProperties.getPageNo(),
                weatherProperties.getDataType(),
                getBaseDate(),
                getBaseTime(),
                nx,
                ny);
    }

    private String getBaseDate() {
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()
            .format(DateTimeFormatter.ofPattern(BASE_DATE_PATTERN));
    }

    private String getBaseTime() {
        LocalDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        return now.minusHours(now.getMinute() < 40 ? 1 : 0).format(DateTimeFormatter.ofPattern(BASE_TIME));
    }

}
