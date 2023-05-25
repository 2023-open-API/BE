package com.pop.planu.domain.service;

import com.pop.planu.domain.entity.Member;
import com.pop.planu.domain.entity.Schedule;
import com.pop.planu.domain.repository.MemberRepository;
import com.pop.planu.domain.repository.ScheduleRepository;
import com.pop.planu.global.exception.IdNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;


//    @Transactional
//    public Long save(Schedule schedule){
//        Member member = memberRepository.findById(schedule.getMemberId())
//                .orElseThrow(()->new IdNotFoundException("회원 정보가 없습니다."));
////        Schedule schedule = Schedule.builder()
////                title().build();
//    }
}
