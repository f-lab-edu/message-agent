package com.kth.message.weather.service.weather;

import com.kth.message.dto.cache.WeatherCacheDto;
import com.kth.message.dto.weather.WeatherInfoDto;
import com.kth.message.dto.weather.request.WeatherDto;
import com.kth.message.entity.Geometry;
import com.kth.message.weather.properties.WeatherProperties;
import com.kth.message.weather.repository.GeometryRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {

	private final static String BASE_DATE_PATTERN = "yyyyMMdd";
	private final static String BASE_TIME = "HH'00'";

	private final WebClient webClient;
	private final WeatherProperties weatherProperties;
	private final GeometryRepository geometryRepository;

	public WeatherService(WebClient.Builder webClientBuilder, WeatherProperties weatherProperties, GeometryRepository geometryRepository) {
		this.webClient = webClientBuilder.baseUrl("http://apis.data.go.kr").build();
		this.weatherProperties = weatherProperties;
		this.geometryRepository = geometryRepository;
	}

	public Mono<WeatherInfoDto> getWeatherInfoDto(WeatherDto weatherDto) {
		String location = weatherDto.getLocation();

		Geometry geometry = findByGeometryByLocation(location);

		return getWeatherInfo(geometry.getNx(), geometry.getNy())
			.map(weatherInfo -> {
				JSONObject jsonObject = new JSONObject(weatherInfo.getWeatherData());
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
			});
	}

	public Geometry findByGeometryByLocation(String location) {
		String[] parts = location.split(" ", 3);

		String regionCity = parts.length > 0 ? parts[0] : "";
		String regionTown = parts.length > 1 ? parts[1] : "";
		String regionVillage = parts.length > 2 ? parts[2] : "";

		return geometryRepository.findByRegionCityAndRegionTownAndRegionVillage(regionCity, regionTown, regionVillage);
	}

	public Mono<WeatherCacheDto> getWeatherInfo(String nx, String ny) {
		return getWeatherApiData(nx, ny)
			.flatMap(weatherApiData -> {
				LocalDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
				if (isCacheHit(weatherApiData.getLocalDateTime(), now)) {
					return evictWeatherCache(nx, ny).then(getWeatherApiData(nx, ny));
				}
				return Mono.just(weatherApiData);
			});
	}

	private boolean isCacheHit(LocalDateTime cachedTime, LocalDateTime now) {
		int cachedHours = cachedTime.minusHours(cachedTime.getMinute() < 40 ? 1 : 0).getHour();
		int nowHours = now.minusHours(now.getMinute() < 40 ? 1 : 0).getHour();
		return cachedHours != nowHours;
	}

	@Cacheable(value="weatherLocation", key = "#nx.concat('_').concat('#ny')")
	public Mono<WeatherCacheDto> getWeatherApiData(String nx, String ny) {
		return webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path("/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst")
				.queryParam("serviceKey", weatherProperties.getServiceKey())
				.queryParam("numOfRows", weatherProperties.getNumOfRows())
				.queryParam("pageNo", weatherProperties.getPageNo())
				.queryParam("dataType", weatherProperties.getDataType())
				.queryParam("base_date", getBaseDate())
				.queryParam("base_time", getBaseTime())
				.queryParam("nx", nx)
				.queryParam("ny", ny)
				.build())
			.retrieve()
			.bodyToMono(String.class)
			.map(WeatherCacheDto::new);
	}

	@CacheEvict(value = "weatherCache", key = "#nx.concat('-').concat(#ny)")
	public Mono<Void> evictWeatherCache(String nx, String ny) {
		return Mono.empty();
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
