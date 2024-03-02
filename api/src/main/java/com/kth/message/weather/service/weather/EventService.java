package com.kth.message.weather.service.weather;

import com.kth.message.dto.event.AlarmSuccessInfoDto;
import com.kth.message.dto.event.UserInfoDto;
import com.kth.message.dto.event.request.EventInfoDto;
import com.kth.message.dto.event.request.RequestEventDto;
import com.kth.message.entity.Member;
import com.kth.message.weather.repository.MemberRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class EventService {

	private final String restApiKey;

	private final MemberRepository memberRepository;
	private final WebClient webClient;

	public EventService(MemberRepository memberRepository,
		@Value("${kakao.rest-api.key}") String restApiKey) {
		this.restApiKey = restApiKey;
		this.memberRepository = memberRepository;
		this.webClient = WebClient.builder().build();
	}

	@Transactional
	public Mono<AlarmSuccessInfoDto> getEventSuccessMessage(RequestEventDto requestEventDto) {
		return Mono.fromCallable(() -> {
			EventInfoDto eventInfoDto = requestEventDto.getEventInfoDto();
			UserInfoDto userInfoDto = requestEventDto.getUserInfoDto();
			String botId = requestEventDto.getBotId();

			Member member = new Member(botId,
				userInfoDto.getId(),
				userInfoDto.getType(),
				eventInfoDto.getLocation(),
				eventInfoDto.getTime(),
				eventInfoDto.getInfoType(),
				eventInfoDto.getEventType());

			Member memberInfo = memberRepository.save(member);

			return new AlarmSuccessInfoDto(memberInfo.getId(),
				memberInfo.getEventType(),
				memberInfo.getLocation(),
				memberInfo.getTime());
		}).subscribeOn(Schedulers.boundedElastic());
	}

	@Scheduled(cron = "0 0 * * * *")
	public void sendEvent() {
		LocalDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
		String hour = String.valueOf(now.getHour());
		List<Member> members = memberRepository.findAllByEventTypeAndTime("날씨", hour);

		return;
	}
}
