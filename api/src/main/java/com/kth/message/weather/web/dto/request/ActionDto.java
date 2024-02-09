package com.kth.message.weather.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ActionDto<T extends ParamDto> {

    private final T paramDto;

    @JsonCreator
    public ActionDto(@JsonProperty("params") T paramDto) {
        this.paramDto = paramDto;
    }

    public T checkType() {
        return paramDto;
    }
}
