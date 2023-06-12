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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CNUElearningCrawlerService crawlerService;
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
        // 테이블에서 가져오기
        List<ScheduleResponse> fromScheduleTable = getFromScheduleTable(memberId, monthDate);

        // 이러닝 todolist 크롤링 해오기
        List<ScheduleResponse> fromCrawler = getFromCrawler(memberId, monthDate);

        // 두개 결과 합치기
        fromScheduleTable.addAll(fromCrawler);
        return fromScheduleTable;
    }

    private List<ScheduleResponse> getFromCrawler(Long memberId, LocalDate monthDate) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IdNotFoundException("회원을 찾을 수 없습니다."));
        List<ScheduleResponse> crawlingList = crawlerService.getTodoList(member.getStudentId().toString(), member.getPassword());

        LocalDate startOfMonth = monthDate.withDayOfMonth(1);
        LocalDate endOfMonth = monthDate.withDayOfMonth(1).plusMonths(1).minusDays(1);

        List<ScheduleResponse> fromCrawler = new ArrayList<>();
        // 크롤링 데이터는 startDate == endDate
        for(ScheduleResponse response: crawlingList) {
            LocalDate strDate = LocalDate.parse(response.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")) ;
            if(strDate.compareTo(startOfMonth) >= 0 && strDate.compareTo(endOfMonth) <= 0) {
                fromCrawler.add(response);
            }
        }
        return fromCrawler;
    }


    private List<ScheduleResponse> getFromScheduleTable(Long memberId, LocalDate monthDate) {
        LocalDate startOfMonth = monthDate.withDayOfMonth(1);
        LocalDate endOfMonth = monthDate.withDayOfMonth(1).plusMonths(1).minusDays(1);

        List<Schedule> scheduleList_SLTELTE = scheduleRepository
                .findByStartDateLessThanEqualAndEndDateLessThanEqualAndMemberId(
                        startOfMonth, endOfMonth,memberId);
        List<Schedule> scheduleList_SGTEETE = scheduleRepository
                .findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndMemberId(
                        startOfMonth, endOfMonth,memberId);
        List<Schedule> scheduleList = new ArrayList<>();

        scheduleList.addAll(scheduleList_SLTELTE);
        scheduleList.addAll(scheduleList_SGTEETE);


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

    public ScheduleResponse getScheduleById(Long scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()->new IdNotFoundException("일정 정보가 없습니다."));
        return ScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .title(schedule.getTitle())
                .color(schedule.getColor())
                .startDate(schedule.getStartDate().toString())
                .endDate(schedule.getEndDate().toString())
                .build();
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
