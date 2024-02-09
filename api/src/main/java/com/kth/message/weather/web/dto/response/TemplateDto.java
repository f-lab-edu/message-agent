package com.kth.message.weather.web.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateDto<T> {

    private final List<T> outputs = new ArrayList<>();

    public void addOutput(T output) {
        this.outputs.add(output);
    }
}
