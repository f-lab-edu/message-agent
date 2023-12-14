package com.kth.mssage.info.service.weather;

import com.kth.mssage.info.client.WeatherApiClient;
import com.kth.mssage.info.properties.WeatherProperties;
import com.kth.mssage.info.web.dto.info.WeatherInfoDto;
import com.kth.mssage.info.web.dto.request.skill.WeatherDto;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
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

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {

    private final static String BASE_DATE_PATTERN = "yyyyMMdd";
    private final static String BASE_TIME = "HH'00'";

    private final WeatherProperties weatherProperties;
    private final WeatherApiClient weatherApiClient;

    public WeatherInfoDto getWeatherInfoDto(WeatherDto weatherDto) {
        String location = weatherDto.getLocation();

        LocalInfoValue localInfoValue = findByRegion(location);

        String weatherInfo = getWeatherInfo(localInfoValue.getNx(), localInfoValue.getNy());
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

    public LocalInfoValue findByRegion(String location) {
        String[] parts = location.split(" ", 3);

        String regionCity = parts.length > 0 ? parts[0] : "";
        String regionTown = parts.length > 1  ? parts[1] : "";
        String regionVillage = parts.length > 2 ? parts[2] : "";

        return loadSingleWeatherInfo(new LocalInfoKey(regionCity, regionTown, regionVillage));
    }

    @Cacheable(value = "weatherInfo", key = "#localInfoKey")
    public LocalInfoValue loadSingleWeatherInfo(LocalInfoKey localInfoKey) {
        Map<LocalInfoKey, LocalInfoValue> localInfoMap = loadWeatherInfo();
        return localInfoMap.get(localInfoKey);
    }

    @Cacheable(value = "weatherInfo")
    public Map<LocalInfoKey, LocalInfoValue> loadWeatherInfo() {
        Map<LocalInfoKey, LocalInfoValue> localInfoMap = new HashMap<>();
        try (InputStream inputStream = new ClassPathResource("weather.csv").getInputStream();
            Reader reader = new InputStreamReader(inputStream);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                localInfoMap.put(
                    new LocalInfoKey(record.get("region_city"), record.get("region_town"), record.get("region_village")),
                    new LocalInfoValue(record.get("nx"), record.get("ny"))
                );
            }
        } catch (Exception e) {
            //TODO: 예외 처리 로직
        }


        return localInfoMap;
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
        return now.minusHours(now.getMinute() < 40 ? 1 : 0).format(DateTimeFormatter.ofPattern(BASE_TIME));
    }

    @AllArgsConstructor
    class LocalInfoKey {
        private final String regionCity;
        private final String regionTown;
        private final String regionVillage;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LocalInfoKey that = (LocalInfoKey) o;

            String thisConcatenated = regionCity + " " + regionTown + " " + regionVillage;
            String thatConcatenated = that.regionCity + " " + that.regionTown + " " + that.regionVillage;

            return thisConcatenated.equals(thatConcatenated);
        }

        @Override
        public int hashCode() {
            return (regionCity + " " + regionTown + " " + regionVillage).hashCode();
        }
    }

    @Getter
    @AllArgsConstructor
    class LocalInfoValue {
        private final String nx;
        private final String ny;
    }
}
