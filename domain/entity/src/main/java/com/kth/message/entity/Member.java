package com.kth.message.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String botId;
	private String kakaoUserId;
	private String type;
	private String timezone;
	private String time;
	private String infoType;
	private String eventType;

	public Member(String botId, String kakaoUserId, String type, String timezone, String time, String infoType,
		String eventType) {
		this.botId = botId;
		this.kakaoUserId = kakaoUserId;
		this.type = type;
		this.timezone = timezone;
		this.time = time;
		this.infoType = infoType;
		this.eventType = eventType;
	}
}
