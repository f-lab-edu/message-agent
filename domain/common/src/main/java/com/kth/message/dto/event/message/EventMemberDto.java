package com.kth.message.dto.event.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EventMemberDto {

	private final String type;
	private final String id;
}
