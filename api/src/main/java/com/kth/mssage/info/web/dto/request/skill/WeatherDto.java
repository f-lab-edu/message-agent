package com.kth.mssage.info.web.dto.request.skill;

import com.kth.mssage.info.web.dto.request.ParamDto;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class WeatherDto extends ParamDto {

    private final String location;
}
