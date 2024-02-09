package com.kth.message.weather.web.dto.response.skill.simpletext;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SimpleTextDto {

    private final SimpleTextContentDto simpleText;
}
