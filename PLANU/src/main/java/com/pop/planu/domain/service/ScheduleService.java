package com.pop.planu.domain.service;

import com.pop.planu.domain.controller.response.schedule.ScheduleResponse;
import com.pop.planu.domain.entity.Member;
import com.pop.planu.domain.entity.Schedule;
import com.pop.planu.domain.repository.MemberRepository;
import com.pop.planu.domain.repository.ScheduleRepository;
import com.pop.planu.domain.service.dto.schedule.ScheduleDto;
import com.pop.planu.global.exception.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public long save(ScheduleDto scheduleDto){
        Member member = memberRepository.findById(scheduleDto.getMemberId())
                .orElseThrow(()-> new IdNotFoundException("회원 정보가 없습니다"));
        Schedule schedule = Schedule.builder()
                .title(scheduleDto.getTitle())
                .color(scheduleDto.getColor())
                .startDate(scheduleDto.getStartDate())
                .endDate(scheduleDto.getEndDate())
                .member(member)
                .build();
        return scheduleRepository.save(schedule).getScheduleId();
    }

    public List<ScheduleResponse> getMonthlySchedule(Long memberId, LocalDate monthDate){
        LocalDate startOfMonth = monthDate.withDayOfMonth(1);
        LocalDate endOfMonth = monthDate.withDayOfMonth(1).plusMonths(1).minusDays(1);
        List<Schedule> scheduleList = scheduleRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndMemberId(
                memberId, startOfMonth, endOfMonth);
        List<ScheduleResponse> scheduleResponsesList = new ArrayList<>();

        scheduleList.forEach(
                (schedule -> {
                    scheduleResponsesList.add(
                     ScheduleResponse.builder()
                             .scheduleId(schedule.getScheduleId())
                             .title(schedule.getTitle())
                             .color(schedule.getColor())
                             .startDate(schedule.getStartDate().toString())
                             .endDate(schedule.getEndDate().toString())
                             .build()
                    );
                })
        );
        return scheduleResponsesList;

    }

    @Transactional
    public ScheduleResponse update(ScheduleDto scheduleDto){
        Schedule schedule = scheduleRepository.findById(scheduleDto.getScheduleId())
                .orElseThrow(()->new IdNotFoundException("일정 정보가 없습니다."));
        schedule.update(scheduleDto.getTitle(),scheduleDto.getStartDate(),scheduleDto.getEndDate(),scheduleDto.getColor());
        return ScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .title(schedule.getTitle())
                .color(schedule.getColor())
                .startDate(schedule.getStartDate().toString())
                .endDate(schedule.getEndDate().toString())
                .build();
    }

    public Long delete(Long scheduleId){
        scheduleRepository.deleteById(scheduleId);
        return scheduleId;
    }

}
