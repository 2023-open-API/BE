package com.pop.planu.domain.controller.request.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pop.planu.domain.service.dto.schedule.ScheduleDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {

    @NotBlank
    private String title;

    private String color;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    public ScheduleDto scheduleDto(Long memberId){
        return ScheduleDto.builder()
                .memberId(memberId)
                .title(this.title)
                .color(this.color)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }

    public ScheduleDto scheduleDto(Long memberId, Long scheduleId){
        return ScheduleDto.builder()
                .memberId(memberId)
                .scheduleId(scheduleId)
                .title(this.title)
                .color(this.color)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
