package com.kth.message.dto.event.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EventInfoDto {

	private final String time;
	private final String infoType;
	private final String eventType;
	private final String location;

	@JsonCreator
	public EventInfoDto(
		@JsonProperty("time") String time,
		@JsonProperty("type")String infoType,
		@JsonProperty("event_type")String eventType,
		@JsonProperty("location")String location) {
		this.time = time;
		this.infoType = infoType;
		this.eventType = eventType;
		this.location = location;
	}
}
