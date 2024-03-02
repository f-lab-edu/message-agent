package com.kth.message.dto.event.message;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EventMessageDto {

	private final Event event;
	private final List<EventMemberDto> user;
	private final Map<String, Object> params;

}
