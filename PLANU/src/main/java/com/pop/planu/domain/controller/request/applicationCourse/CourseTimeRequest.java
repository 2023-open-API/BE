package com.pop.planu.domain.controller.request.applicationCourse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pop.planu.domain.service.dto.applicationCourse.CourseTimeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseTimeRequest {

    private String day;

    @Schema(type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime startTime;

    @Schema(type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime endTime;

    public CourseTimeDto toDto() {
        return CourseTimeDto.builder()
                .day(this.day)
                .startTime(this.startTime)
                .endTime(this.endTime).build();
    }
}
