package com.kth.message.dto.event.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RequestEventUserDto {

	private final EventUserInfoDto eventUserInfoDto;
	private final String timezone;

	@JsonCreator
	public RequestEventUserDto(
		@JsonProperty("user") EventUserInfoDto eventUserInfoDto,
		@JsonProperty("timezone") String timezone)
	{
		this.eventUserInfoDto = eventUserInfoDto;
		this.timezone = timezone;
	}
}
