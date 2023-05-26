package com.pop.planu.domain.controller;

import com.pop.planu.domain.auth.AuthMember;
import com.pop.planu.domain.controller.request.schedule.ScheduleRequest;
import com.pop.planu.domain.controller.response.schedule.ScheduleResponse;
import com.pop.planu.domain.service.ScheduleService;
import com.pop.planu.global.web.argumentresolver.Auth;
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
public class ScheduleRestController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ResponseEntity<Void> registerSchedule(@Auth AuthMember authMember, @RequestBody ScheduleRequest scheduleRequest){
        Long scheduleId = scheduleService.save(scheduleRequest.toDto(authMember.getId()));

        URI uri = UriComponentsBuilder.fromPath("/schedule/{scheduleId}")
                .buildAndExpand(scheduleId)
                .toUri();
        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/schedule/{currentDate}")
    public ResponseEntity<List<ScheduleResponse>> getMonthlySchedule(@Auth AuthMember authMember, @PathVariable LocalDate currentDate){
        List<ScheduleResponse> scheduleResponseList = scheduleService.getMonthlySchedule(authMember.getId(), currentDate);
        return ResponseEntity.ok(scheduleResponseList);
    }

    @GetMapping("/schedule/{schedulId}")
    public ResponseEntity<ScheduleResponse> getSchedule(@Auth AuthMember authMember, @PathVariable Long scheduleId){
        ScheduleResponse scheduleResponse = scheduleService.getScheduleById(scheduleId);
        return ResponseEntity.ok(scheduleResponse);
    }

    @PutMapping("/schedule/{schedulId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(@Auth AuthMember authMember,@PathVariable Long scheduleId, @RequestBody ScheduleRequest scheduleRequest){
        ScheduleResponse updateSchedule = scheduleService.update(scheduleRequest.toDto(authMember.getId(),scheduleId));
        return ResponseEntity.ok(updateSchedule);
    }

    @DeleteMapping("/schedule/{schedulId}")
    public ResponseEntity<Long> deleteSchedule(@Auth AuthMember authMember,@PathVariable Long schedulId){
        Long deleteId = scheduleService.delete(schedulId);
        return ResponseEntity.ok(deleteId);
    }

}
