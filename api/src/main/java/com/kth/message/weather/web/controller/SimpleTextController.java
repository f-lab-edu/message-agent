package com.kth.message.weather.web.controller;

import com.kth.message.dto.weather.request.RequestActionDto;
import com.kth.message.dto.weather.response.ResponseResultDto;
import com.kth.message.weather.service.SimpleTextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/simple-text")
@RestController
public class SimpleTextController {

    private final SimpleTextService simpleTextService;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/message")
    public Mono<ResponseResultDto> requestMessageInfo(
        @RequestBody RequestActionDto requestActionDto) {
        return simpleTextService.createMessage(requestActionDto)
            .map(ResponseResultDto::createResultMessage);
    }
}
