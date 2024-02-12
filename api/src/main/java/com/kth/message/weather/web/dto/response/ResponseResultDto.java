package com.kth.message.weather.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseResultDto {

    private final String version = "2.0";
    private final TemplateDto template;

    public static ResponseResultDto createResultMessage(TemplateDto templateDto) {
        return ResponseResultDto.builder()
                .template(templateDto)
                .build();
    }
}
