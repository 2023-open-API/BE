package com.pop.planu.domain.service.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDto {
    private Long memberId;
    private Long scheduleId;
    private String title;
    private String color;
    private LocalDate startDate;
    private LocalDate endDate;

}
