package com.kth.message.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlarmSuccessInfoDto {

	private final Long alarmId;
	private final String alarmType;
	private final String alarmTimeZone;
	private final String alarmTime;
}
