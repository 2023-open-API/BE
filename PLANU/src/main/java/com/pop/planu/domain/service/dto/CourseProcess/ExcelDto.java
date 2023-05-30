package com.pop.planu.domain.service.dto.CourseProcess;

import com.pop.planu.domain.service.dto.applicationCourse.CourseTimeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcelDto {

    private String professor;

    private List<CourseTimeDto> courseTimeDtos;

}
