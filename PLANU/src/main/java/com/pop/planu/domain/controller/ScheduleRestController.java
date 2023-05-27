package com.pop.planu.domain.controller;

import com.pop.planu.domain.auth.AuthMember;
import com.pop.planu.domain.controller.request.schedule.ScheduleRequest;
import com.pop.planu.domain.controller.response.schedule.ScheduleResponse;
import com.pop.planu.domain.service.ScheduleService;
import com.pop.planu.global.web.argumentresolver.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "일정관리", description = "달력 표시 일정 관련 api")
public class ScheduleRestController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    @Operation(summary = "일정 등록", description = "새로운 일정 등록")
    public ResponseEntity<Void> registerSchedule(@Auth AuthMember authMember, @RequestBody ScheduleRequest scheduleRequest){
        Long scheduleId = scheduleService.save(scheduleRequest.toDto(authMember.getId()));

        URI uri = UriComponentsBuilder.fromPath("/schedule/{scheduleId}")
                .buildAndExpand(scheduleId)
                .toUri();
        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/schedule/month/{currentDate}")
    @Operation(summary = "월별 일정 조회", description = "현재 일자(yyyy-mm-dd)를 기준으로 월별 일정 을 조회한다.")
    public ResponseEntity<List<ScheduleResponse>> getMonthlySchedule(@Auth AuthMember authMember, @PathVariable LocalDate currentDate){
        List<ScheduleResponse> scheduleResponseList = scheduleService.getMonthlySchedule(authMember.getId(), currentDate);
        return ResponseEntity.ok(scheduleResponseList);
    }

    @GetMapping("/schedule/{scheduleId}")
    @Operation(summary = "일정 조회", description = "일정 id를 기준으로 일정을 조회한다.")
    public ResponseEntity<ScheduleResponse> getSchedule(@Auth AuthMember authMember, @PathVariable Long scheduleId){
        ScheduleResponse scheduleResponse = scheduleService.getScheduleById(scheduleId);
        return ResponseEntity.ok(scheduleResponse);
    }

    @PutMapping("/schedule/{scheduleId}")
    @Operation(summary = "일정 수정", description = "일정 id를 기준으로 일정을 수정한다.")
    public ResponseEntity<ScheduleResponse> updateSchedule(@Auth AuthMember authMember,@PathVariable Long scheduleId, @RequestBody ScheduleRequest scheduleRequest){
        ScheduleResponse updateSchedule = scheduleService.update(scheduleRequest.toDto(authMember.getId(),scheduleId));
        return ResponseEntity.ok(updateSchedule);
    }

    @DeleteMapping("/schedule/{scheduleId}")
    @Operation(summary = "일정 삭제", description = "일정 id를 기준으로 일정을 삭제한다.")
    public ResponseEntity<Long> deleteSchedule(@Auth AuthMember authMember,@PathVariable Long scheduleId){
        Long deleteId = scheduleService.delete(scheduleId);
        return ResponseEntity.ok(deleteId);
    }

}
