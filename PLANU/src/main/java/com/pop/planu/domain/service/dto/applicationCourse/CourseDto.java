package com.pop.planu.domain.service.dto.applicationCourse;

import com.pop.planu.domain.entity.ApplicationCourse;
import com.pop.planu.domain.entity.CourseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {

    private Long code;

    private String name;

    private String professor;

    private List<CourseTimeDto> courseTimeDtos;

    public ApplicationCourse toEntity() {
        ApplicationCourse applicationCourse = ApplicationCourse.builder()
                .code(this.code)
                .name(this.name)
                .professor(this.professor).build();
        List<CourseTime> courseTimes = new ArrayList<>();
        for(CourseTimeDto courseTimeDto: courseTimeDtos) {
            CourseTime courseTime = courseTimeDto.toEntity();
            courseTime.setApplicationCourse(applicationCourse);
            courseTimes.add(courseTime);
        }
        applicationCourse.setCourseTimeList(courseTimes);
        return applicationCourse;
    }

}
