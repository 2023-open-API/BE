package com.pop.planu.domain.controller.response.CourseProcess;

import com.pop.planu.domain.controller.response.applicationCourse.CourseTimeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponse {
    private String grade;
    private String code;
    private String room;
    private String name;
    private String professor;
    private Double credit;
    private List<CourseTimeResponse> courseTimeResponses;
}
