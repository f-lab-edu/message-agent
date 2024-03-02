package com.kth.message.weather.service.weather;

import com.kth.message.dto.event.AlarmSuccessInfoDto;
import com.kth.message.dto.event.UserInfoDto;
import com.kth.message.dto.event.request.EventInfoDto;
import com.kth.message.dto.event.request.RequestEventDto;
import com.kth.message.entity.Member;
import com.kth.message.weather.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@AllArgsConstructor
@Service
public class EventService {

	private final MemberRepository memberRepository;

	@Transactional
	public Mono<AlarmSuccessInfoDto> getEventSuccessMessage(RequestEventDto requestEventDto) {
		return Mono.fromCallable(() -> {
			EventInfoDto eventInfoDto = requestEventDto.getEventInfoDto();
			UserInfoDto userInfoDto = requestEventDto.getUserInfoDto();
			String botId = requestEventDto.getBotId();

			Member member = new Member(botId,
				userInfoDto.getId(),
				userInfoDto.getType(),
				userInfoDto.getTimezone(),
				eventInfoDto.getTime(),
				eventInfoDto.getInfoType(),
				eventInfoDto.getEventType());

			Member memberInfo = memberRepository.save(member);

			return new AlarmSuccessInfoDto(memberInfo.getId(),
				memberInfo.getEventType(),
				memberInfo.getTimezone(),
				memberInfo.getTime());
		}).subscribeOn(Schedulers.boundedElastic());
	}
}
