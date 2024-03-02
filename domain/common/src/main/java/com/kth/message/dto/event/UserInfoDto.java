package com.kth.message.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoDto {

	private final String id;
	private final String type;

}
