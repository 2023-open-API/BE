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

    private String name; // 과목명

    private String code; // 과목 번호

    private String room; // 분반

    private double credit; // 학점

    private String courseTime; // 강의시간

    private String professor; // 교수명

    private String department; // 학과

}
