package com.kth.message.dto.event.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RequestEventBotDto {

	private final String botId;

	@JsonCreator
	public RequestEventBotDto(@JsonProperty("id") String botId) {
		this.botId = botId;
	}
}
