package com.kth.message.dto.event.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kth.message.dto.event.UserInfoDto;
import lombok.Getter;

@Getter
public class RequestEventDto {

	private final RequestEventUserDto requestEventUserDto;
	private final RequestEventBotDto requestEventBotDto;
	private final RequestEventActionDto requestEventActionDto;

	@JsonCreator
	public RequestEventDto(
		@JsonProperty("userRequest") RequestEventUserDto requestEventUserDto,
		@JsonProperty("bot") RequestEventBotDto requestEventBotDto,
		@JsonProperty("action") RequestEventActionDto requestEventActionDto) {
		this.requestEventUserDto = requestEventUserDto;
		this.requestEventBotDto = requestEventBotDto;
		this.requestEventActionDto = requestEventActionDto;
	}

	public EventInfoDto getEventInfoDto() {
		return requestEventActionDto.getEventInfoDto();
	}

	public UserInfoDto getUserInfoDto() {
		return new UserInfoDto(
			requestEventUserDto.getUserId(),
			requestEventUserDto.getUserType()
		);
	}

	public String getBotId() {
		return requestEventBotDto.getBotId();
	}
}
