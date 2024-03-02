package com.kth.message.weather.web.controller;

import com.kth.message.dto.event.request.RequestEventDto;
import com.kth.message.dto.weather.response.ResponseResultDto;
import com.kth.message.weather.service.SimpleTextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class EventController {

	private final SimpleTextService simpleTextService;

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/alarm/weather")
	public Mono<ResponseResultDto> requestMessageInfo(@RequestBody RequestEventDto requestEventDto) {
		return simpleTextService.createEventSuccessMessage(requestEventDto)
			.map(ResponseResultDto::createResultMessage);
	}
}
