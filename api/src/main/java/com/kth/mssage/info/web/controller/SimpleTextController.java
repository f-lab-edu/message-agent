package com.kth.mssage.info.web.controller;

import com.kth.mssage.info.service.SimpleTextService;
import com.kth.mssage.info.web.dto.request.ParamDto;
import com.kth.mssage.info.web.dto.request.RequestActionDto;
import com.kth.mssage.info.web.dto.request.skill.WeatherDto;
import com.kth.mssage.info.web.dto.response.ResponseResultDto;
import com.kth.mssage.info.web.dto.response.skill.simpletext.SimpleTextDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/simple-text")
@RestController
public class SimpleTextController {

    private final SimpleTextService simpleTextService;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/message")
    public ResponseResultDto<SimpleTextDto> requestMessageInfo(@RequestBody RequestActionDto<ParamDto> requestActionDto) {

        if (requestActionDto.findTypeParamDto() instanceof WeatherDto weatherDto){
            return ResponseResultDto.createResultMessage(
                    simpleTextService.createWeatherMessage(weatherDto));
        }

        //TODO: 타입을 체크했을때 default로 어떤값이 들어가야하나?
        return ResponseResultDto.createResultMessage(
                simpleTextService.createWeatherMessage(
                        (WeatherDto) requestActionDto.findTypeParamDto()));
    }
}
