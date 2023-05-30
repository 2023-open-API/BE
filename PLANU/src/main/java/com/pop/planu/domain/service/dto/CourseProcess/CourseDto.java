package com.pop.planu.domain.service.dto.CourseProcess;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {

    private String grade; // 학년

    private String name;

    private String code; // 과목 번호

    private String room; // 분반

    private double credit; // 학점
}
