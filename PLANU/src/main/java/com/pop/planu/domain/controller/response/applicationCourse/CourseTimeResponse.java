package com.pop.planu.domain.controller.response.applicationCourse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseTimeResponse {
    private Long courseTimeId;
    private String day;
    private Long startTime;
    private Long endTime;
}
