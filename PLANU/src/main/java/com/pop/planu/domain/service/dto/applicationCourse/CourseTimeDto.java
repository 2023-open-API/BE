package com.pop.planu.domain.service.dto.applicationCourse;

import com.pop.planu.domain.entity.CourseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseTimeDto {

    private Long applicationCourseId;

    private String day;

    private Long startTime;

    private Long endTime;

    public CourseTime toEntity() {
        return CourseTime.builder()
                .day(this.day)
                .startTime(this.startTime)
                .endTime(this.endTime).build();
    }
}
