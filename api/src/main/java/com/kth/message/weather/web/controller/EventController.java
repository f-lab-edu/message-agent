package com.kth.message.weather.web.controller;

import com.kth.message.dto.event.request.RequestEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EventController {

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/alarm/weather")
	public void requestMessageInfo(@RequestBody RequestEventDto requestEventDto) {
		return;
	}
}
