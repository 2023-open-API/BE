package com.pop.planu.domain.service;

import com.pop.planu.domain.controller.response.CourseProcess.CourseResponse;
import com.pop.planu.domain.controller.response.applicationCourse.CourseTimeResponse;
import com.pop.planu.domain.service.dto.CourseProcess.ExcelDto;
import com.pop.planu.domain.service.dto.applicationCourse.CourseTimeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.json.simple.parser.ParseException;
import com.pop.planu.domain.service.dto.CourseProcess.CourseDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseProcessService {

    private final CNUSyllabusAPI cnuSyllabusAPI;
    private final ExcelReadService excelReadService;

    public List<CourseResponse> getAllCourseByYearAndSemester(Long year, Long semester) throws ParseException, IOException, URISyntaxException {
        // 강의계획 open api 결과
        List<CourseDto> courseDtos = cnuSyllabusAPI.getCourseList(year, semester);

        // 엑셀 강의편람 결과
        Map<String, ExcelDto> excelDtoMap = excelReadService.read(year, semester);

        // 공통 결과 매칭
        List<CourseResponse> courseResponses = new ArrayList<>();
        for(CourseDto courseDto: courseDtos) {
            // key : code-room
            String key = courseDto.getCode() + "-"+courseDto.getRoom();
            ExcelDto excelDto = excelDtoMap.get(key);
            // 엑셀에 존재하지 않는 강의인 경우
            if(excelDto == null) continue;
            List<CourseTimeResponse> courseTimeResponses = new ArrayList<>();
            for(CourseTimeDto courseTimeDto: excelDto.getCourseTimeDtos()) {
                CourseTimeResponse courseTimeResponse = CourseTimeResponse.builder()
                        .day(courseTimeDto.getDay())
                        .startTime(courseTimeDto.getStartTime())
                        .endTime(courseTimeDto.getEndTime())
                        .build();
                courseTimeResponses.add(courseTimeResponse);
            }
            CourseResponse courseResponse = CourseResponse.builder()
                    .code(courseDto.getCode())
                    .room(courseDto.getRoom())
                    .name(courseDto.getName())
                    .grade(courseDto.getGrade())
                    .credit(courseDto.getCredit())
                    .professor(excelDto.getProfessor())
                    .courseTimeResponses(courseTimeResponses)
                    .build();
            courseResponses.add(courseResponse);
        }
        return courseResponses;
    }




}
