package com.pop.planu.domain.controller.response.applicationCourse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationCourseResponse {
    private Long applicationCourseId;
    private Long code;
    private String name;
    private String professor;
    private List<CourseTimeResponse> courseTimes;
}
