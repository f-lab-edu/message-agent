package com.kth.mssage.info.service.weather;

import com.kth.mssage.info.client.WeatherApiClient;
import com.kth.mssage.info.properties.WeatherProperties;
import com.kth.mssage.info.web.dto.info.LocalInfoDto;
import com.kth.mssage.info.web.dto.info.WeatherInfoDto;
import com.kth.mssage.info.web.dto.request.skill.WeatherDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {

    private final static String BASE_DATE_PATTERN = "yyyyMMdd";
    private final static String BASE_MINUTE = "00";

    private final WeatherProperties weatherProperties;
    private final WeatherApiClient weatherApiClient;

    public WeatherInfoDto createWeatherInfoDto(WeatherDto weatherDto) {
        String location = weatherDto.getLocation();

        LocalInfoDto localInfoDto = findByRegion(location);

        String weatherInfo = getWeatherInfo(localInfoDto.getNx(), localInfoDto.getNy());
        log.debug("날씨 Json data "+ weatherInfo);

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

    public LocalInfoDto findByRegion(String location) {
        String[] parts = location.split(" ", 3);

        String regionCity = parts.length > 0 ? parts[0] : "";
        String regionTown = parts.length > 1  ? parts[1] : "";
        String regionVillage = parts.length > 2 ? parts[2] : "";

        List<LocalInfoDto> allData = loadWeatherInfo();
        return allData.stream()
                .filter(localDto -> localDto.getRegionCity().equals(regionCity) &&
                        localDto.getRegionTown().equals(regionTown) &&
                        localDto.getRegionVillage().equals(regionVillage))
                .findFirst()
                .orElseThrow(RuntimeException::new); //TODO: 예외 처리 로직 필요
    }

    @Cacheable(value = "weatherInfo")
    public List<LocalInfoDto> loadWeatherInfo() {
        List<LocalInfoDto> localInfoDtoList = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(new ClassPathResource("weather.csv").getFile().toPath());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                LocalInfoDto localDto = new LocalInfoDto(
                        record.get("region_city"), record.get("region_town"), record.get("region_village"),
                        record.get("nx"), record.get("ny")
                );

                localInfoDtoList.add(localDto);
            }
        } catch (Exception e) {
            //TODO: 예외 처리 로직
        }

        return localInfoDtoList;
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
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(BASE_DATE_PATTERN));
    }

    private String getBaseTime() {
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
