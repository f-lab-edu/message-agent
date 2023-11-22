package com.kth.mssage.info.web.dto.response.skill.textcard;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TextCardButtonDto {
    private final String action;
    private final String label;
    private final String webLinkUrl;
}
