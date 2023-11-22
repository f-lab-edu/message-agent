package com.kth.mssage.info.web.dto.response.skill.textcard;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class TextCardContentDto {

    private final String title;
    private final String description;
    private final List<TextCardButtonDto> buttons = new ArrayList<>();

    public void addButton(TextCardButtonDto textCardButtonDto) {
        this.buttons.add(textCardButtonDto);
    }
}
