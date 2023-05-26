package com.pop.planu.domain.controller.request.applicationCourse;

import com.pop.planu.domain.service.dto.applicationCourse.CourseDto;
import com.pop.planu.domain.service.dto.applicationCourse.CourseTimeDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {

    @NotNull
    private Long code;

    @NotBlank
    private String name;

    @NotBlank
    private String professor;

    @Valid
    private List<CourseTimeRequest> courseTimeRequests;

    public CourseDto toDto(){
        List<CourseTimeDto> courseTimeDtos = new ArrayList<>();
        for(CourseTimeRequest courseTimeRequest: this.courseTimeRequests) {
            courseTimeDtos.add(courseTimeRequest.toDto());
        }
        return CourseDto.builder()
                .code(this.code)
                .name(this.name)
                .professor(this.professor)
                .courseTimeDtos(courseTimeDtos).build();
    }
}
