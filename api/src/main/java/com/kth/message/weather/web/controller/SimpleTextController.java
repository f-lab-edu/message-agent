package com.kth.message.weather.web.controller;

import com.kth.message.weather.service.SimpleTextService;
import com.kth.message.weather.web.dto.request.ParamDto;
import com.kth.message.weather.web.dto.response.ResponseResultDto;
import com.kth.message.weather.web.dto.response.skill.simpletext.SimpleTextDto;
import com.kth.message.weather.web.dto.request.RequestActionDto;
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
    public Mono<ResponseResultDto<SimpleTextDto>> requestMessageInfo(
        @RequestBody RequestActionDto<ParamDto> requestActionDto) {
        return simpleTextService.createMessage(requestActionDto)
            .map(ResponseResultDto::createResultMessage);
    }
}
