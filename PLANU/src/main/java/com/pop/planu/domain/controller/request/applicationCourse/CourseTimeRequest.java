package com.pop.planu.domain.controller.request.applicationCourse;

import com.pop.planu.domain.service.dto.applicationCourse.CourseTimeDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseTimeRequest {

    @NotBlank
    private String day;

    @NotNull
    private Long startTime;

    @NotNull
    private Long endTime;

    public CourseTimeDto toDto() {
        return CourseTimeDto.builder()
                .day(this.day)
                .startTime(this.startTime)
                .endTime(this.endTime).build();
    }
}
