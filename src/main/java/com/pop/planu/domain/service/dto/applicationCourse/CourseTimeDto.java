package com.pop.planu.domain.service.dto.applicationCourse;

import com.pop.planu.domain.entity.CourseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseTimeDto {

    private String day;

    private LocalTime startTime;

    private LocalTime endTime;

    private String location;

    public CourseTime toEntity() {
        return CourseTime.builder()
                .day(this.day)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .location(this.location)
                .build();
    }
}
