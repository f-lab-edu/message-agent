package com.kth.message.dto.event.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EventUserInfoDto {

	private final Long id;
	private final String type;

	@JsonCreator
	public EventUserInfoDto(
		@JsonProperty("id") Long id,
		@JsonProperty("type") String type) {
		this.id = id;
		this.type = type;
	}
}
