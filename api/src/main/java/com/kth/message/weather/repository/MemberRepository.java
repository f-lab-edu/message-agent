package com.kth.message.weather.repository;

import com.kth.message.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

	List<Member> findAllByEventTypeAndTime(String weather, String hour);

}
