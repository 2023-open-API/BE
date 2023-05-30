package com.pop.planu.domain.controller;

import com.pop.planu.domain.auth.AuthMember;
import com.pop.planu.domain.controller.request.applicationCourse.ApplicationCourseRequest;
import com.pop.planu.domain.controller.response.applicationCourse.ApplicationCourseResponse;
import com.pop.planu.domain.service.ApplicationCourseService;
import com.pop.planu.global.web.argumentresolver.Auth;
import io.swagger.v3.oas.annotations.Operation;
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
    private ResponseEntity<URI> save(@Auth AuthMember authMember, @RequestBody ApplicationCourseRequest applicationCourseRequest) {
        Long courseId = applicationCourseService.save(authMember.getId(), applicationCourseRequest.toDto());

        URI uri = UriComponentsBuilder.fromPath("/applicationCourse/{courseId}")
                .buildAndExpand(courseId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/applicationCourse")
    @Operation(summary = "시간표 강의 조회", description = "멤버별 시간표의 강의 목록을 조회합니다.")
    private ResponseEntity<List<ApplicationCourseResponse>> getAllApplicationCourseByMemberId(@Auth AuthMember authMember){
        List<ApplicationCourseResponse> applicationCourseRespons = applicationCourseService.getAllApplicationCourseByMemberId(authMember.getId());
        return ResponseEntity.ok(applicationCourseRespons);
    }

    @GetMapping("/applicationCourse/{courseId}")
    @Operation(summary = "시간표 특정 강의 조회", description = "시간표 강의 중 특정 강의 정보를 조회합니다.")
    private ResponseEntity<ApplicationCourseResponse> getApplicationCourseById(@Auth AuthMember authMember, @PathVariable Long courseId){
        ApplicationCourseResponse applicationCourseResponse = applicationCourseService.getApplicationCourseById(courseId);
        return ResponseEntity.ok(applicationCourseResponse);
    }

    @DeleteMapping("/applicationCourse/{courseId}")
    @Operation(summary = "시간표 강의 삭제", description = "시간표에서 강의를 삭제합니다.")
    private ResponseEntity<Long> deleteApplicatioinCourseById(@Auth AuthMember authMember, @PathVariable Long courseId){
        Long deletedId = applicationCourseService.deleteById(courseId);
        return ResponseEntity.ok(deletedId);
    }
}
