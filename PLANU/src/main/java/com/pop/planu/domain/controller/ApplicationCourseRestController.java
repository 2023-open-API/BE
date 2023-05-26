package com.pop.planu.domain.controller;

import com.pop.planu.domain.auth.AuthMember;
import com.pop.planu.domain.controller.request.applicationCourse.CourseRequest;
import com.pop.planu.domain.controller.response.applicationCourse.CourseResponse;
import com.pop.planu.domain.service.ApplicationCourseService;
import com.pop.planu.global.web.argumentresolver.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApplicationCourseRestController {

    private final ApplicationCourseService applicationCourseService;

    @PostMapping("/applicationCourse")
    private ResponseEntity<URI> save(@Auth AuthMember authMember, @RequestBody CourseRequest courseRequest) {
        Long courseId = applicationCourseService.save(authMember.getId(), courseRequest.toDto());

        URI uri = UriComponentsBuilder.fromPath("/applicationCourse/{courseId}")
                .buildAndExpand(courseId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/applicationCourse")
    private ResponseEntity<List<CourseResponse>> getAllApplicationCourseByMemberId(@Auth AuthMember authMember){
        List<CourseResponse> courseResponses = applicationCourseService.getAllApplicationCourseByMemberId(authMember.getId());
        return ResponseEntity.ok(courseResponses);
    }

    @GetMapping("/applicationCourse/{courseId}")
    private ResponseEntity<CourseResponse> getApplicationCourseById(@Auth AuthMember authMember, @PathVariable Long courseId){
        CourseResponse courseResponse = applicationCourseService.getApplicationCourseById(courseId);
        return ResponseEntity.ok(courseResponse);
    }

    @DeleteMapping("/applicationCourse/{courseId}")
    private ResponseEntity<Long> deleteApplicatioinCourseById(@Auth AuthMember authMember, @PathVariable Long courseId){
        Long deletedId = applicationCourseService.deleteById(courseId);
        return ResponseEntity.ok(deletedId);
    }
}
