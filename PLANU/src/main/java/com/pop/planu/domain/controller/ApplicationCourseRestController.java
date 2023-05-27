package com.pop.planu.domain.controller;

import com.pop.planu.domain.auth.AuthMember;
import com.pop.planu.domain.controller.request.applicationCourse.CourseRequest;
import com.pop.planu.domain.controller.response.applicationCourse.CourseResponse;
import com.pop.planu.domain.service.ApplicationCourseService;
import com.pop.planu.global.web.argumentresolver.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "시간표 강의", description = "시간표 강의 관련 api")
public class ApplicationCourseRestController {
    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(Auth.class);
    }
    private final ApplicationCourseService applicationCourseService;

    @PostMapping("/applicationCourse")
    @Operation(summary = "시간표 강의 저장", description = "시간표에 추가할 강의 정보를 저장합니다.")
    private ResponseEntity<URI> save(@Auth AuthMember authMember, @RequestBody CourseRequest courseRequest) {
        Long courseId = applicationCourseService.save(authMember.getId(), courseRequest.toDto());

        URI uri = UriComponentsBuilder.fromPath("/applicationCourse/{courseId}")
                .buildAndExpand(courseId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/applicationCourse")
    @Operation(summary = "시간표 강의 조회", description = "멤버별 시간표의 강의 목록을 조회합니다.")
    private ResponseEntity<List<CourseResponse>> getAllApplicationCourseByMemberId(@Auth AuthMember authMember){
        List<CourseResponse> courseResponses = applicationCourseService.getAllApplicationCourseByMemberId(authMember.getId());
        return ResponseEntity.ok(courseResponses);
    }

    @GetMapping("/applicationCourse/{courseId}")
    @Operation(summary = "시간표 특정 강의 조회", description = "시간표 강의 중 특정 강의 정보를 조회합니다.")
    private ResponseEntity<CourseResponse> getApplicationCourseById(@Auth AuthMember authMember, @PathVariable Long courseId){
        CourseResponse courseResponse = applicationCourseService.getApplicationCourseById(courseId);
        return ResponseEntity.ok(courseResponse);
    }

    @DeleteMapping("/applicationCourse/{courseId}")
    @Operation(summary = "시간표 강의 삭제", description = "시간표에서 강의를 삭제합니다.")
    private ResponseEntity<Long> deleteApplicatioinCourseById(@Auth AuthMember authMember, @PathVariable Long courseId){
        Long deletedId = applicationCourseService.deleteById(courseId);
        return ResponseEntity.ok(deletedId);
    }
}
