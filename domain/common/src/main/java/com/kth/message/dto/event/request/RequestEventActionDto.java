package com.kth.message.dto.event.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RequestEventActionDto {

	private final EventInfoDto eventInfoDto;

	@JsonCreator
	public RequestEventActionDto(@JsonProperty("params") EventInfoDto eventInfoDto) {
		this.eventInfoDto = eventInfoDto;
	}
}
