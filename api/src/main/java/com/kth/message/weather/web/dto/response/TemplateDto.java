package com.kth.message.weather.web.dto.response;

import com.kth.message.weather.web.dto.response.skill.simpletext.SimpleTextDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateDto {

    private final List<SimpleTextDto> outputs = new ArrayList<>();

    public void addOutput(SimpleTextDto output) {
        this.outputs.add(output);
    }
}
