package com.kth.message.dto.weather.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SimpleTextDto {

    private final SimpleTextContentDto simpleText;
}
