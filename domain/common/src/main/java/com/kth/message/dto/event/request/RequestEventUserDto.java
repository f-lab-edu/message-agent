package com.kth.message.dto.event.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RequestEventUserDto {

	private final EventUserInfoDto eventUserInfoDto;


	@JsonCreator
	public RequestEventUserDto(
		@JsonProperty("user") EventUserInfoDto eventUserInfoDto)
	{
		this.eventUserInfoDto = eventUserInfoDto;
	}

	public String getUserId() {
		return eventUserInfoDto.getId();
	}

	public String getUserType() {
		return eventUserInfoDto.getType();
	}
}
