package com.pop.planu.domain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pop.planu.domain.controller.response.CourseProcess.CourseResponse;
import com.pop.planu.domain.service.CourseProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "강의 계획", description = "cnu 강의계획 api 관련 api")
public class CourseProcessRestController {

    private final CourseProcessService courseProcessService;

    @GetMapping("/applicationCourse/{year}/{semester}")
    @Operation(summary = "전체 강의 조회", description = "강의계획 api & 수강편람.xlxs 매치된 강의 조회")
    public ResponseEntity<List<CourseResponse>> getCourses(@PathVariable Long year, @PathVariable Long semester) throws ParseException, IOException, URISyntaxException {
        List<CourseResponse> courseResponses = courseProcessService.getAllCourseByYearAndSemester(year, semester);
        return ResponseEntity.ok(courseResponses);
    }


}
