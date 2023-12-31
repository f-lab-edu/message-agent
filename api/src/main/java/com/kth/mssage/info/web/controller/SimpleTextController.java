package com.kth.mssage.info.web.controller;

import com.kth.mssage.info.service.SimpleTextService;
import com.kth.mssage.info.web.dto.request.ParamDto;
import com.kth.mssage.info.web.dto.request.RequestActionDto;
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
        return ResponseResultDto.createResultMessage(simpleTextService.createMessage(requestActionDto));
    }
}
