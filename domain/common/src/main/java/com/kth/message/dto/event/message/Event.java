package com.kth.message.dto.event.message;

import java.util.Map;
import lombok.Getter;

@Getter
public class Event {

	private final String name;
	private final Map<String, String> data;

	public Event(Map<String, String> data) {
		this.name = "알려봇";
		this.data = data;
	}
}
