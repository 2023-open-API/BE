package com.pop.planu.domain.service;

import com.pop.planu.domain.controller.response.applicationCourse.ApplicationCourseResponse;
import com.pop.planu.domain.controller.response.applicationCourse.CourseTimeResponse;
import com.pop.planu.domain.entity.ApplicationCourse;
import com.pop.planu.domain.entity.CourseTime;
import com.pop.planu.domain.entity.Member;
import com.pop.planu.domain.repository.ApplicationCourseRepository;
import com.pop.planu.domain.repository.MemberRepository;
import com.pop.planu.domain.service.dto.applicationCourse.ApplicationCourseDto;
import com.pop.planu.global.exception.IdNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ApplicationCourseService {

    private final ApplicationCourseRepository applicationCourseRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Long memberId, ApplicationCourseDto applicationCourseDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IdNotFoundException("회원 정보가 없습니다."));
        ApplicationCourse applicationCourse = applicationCourseDto.toEntity();
        applicationCourse.setMember(member);
        return applicationCourseRepository.save(applicationCourse).getId();
    }

    public ApplicationCourseResponse getApplicationCourseById(Long courseId) {
        ApplicationCourse applicationCourse = applicationCourseRepository.findById(courseId)
                .orElseThrow(() -> new IdNotFoundException("수강 신청한 강의 정보가 없습니다."));
        return ApplicationCourseResponse.builder()
                .applicationCourseId(applicationCourse.getId())
                .code(applicationCourse.getCode())
                .name(applicationCourse.getName())
                .professor(applicationCourse.getProfessor())
                .courseTimes(getCourseTimeResponses(applicationCourse.getCourseTimeList()))
                .build();
    }

    public List<ApplicationCourseResponse> getAllApplicationCourseByMemberId(Long memberId) {
        List<ApplicationCourseResponse> applicationCourseRespons = new ArrayList<>();
        List<ApplicationCourse> applicationCourses = applicationCourseRepository.findAllByMemberId(memberId)
                .orElse(new ArrayList<>());
        applicationCourses.forEach((course) -> {
            ApplicationCourseResponse applicationCourseResponse = ApplicationCourseResponse.builder()
                    .applicationCourseId(course.getId())
                    .code(course.getCode())
                    .name(course.getName())
                    .professor(course.getProfessor())
                    .courseTimes(getCourseTimeResponses(course.getCourseTimeList()))
                    .build();
            applicationCourseRespons.add(applicationCourseResponse);
        });
        return applicationCourseRespons;
    }
    private List<CourseTimeResponse> getCourseTimeResponses(List<CourseTime> courseTimeList) {
        List<CourseTimeResponse> courseTimeResponses = new ArrayList<>();
        for(CourseTime courseTime: courseTimeList) {
            CourseTimeResponse courseTimeResponse = CourseTimeResponse.builder()
                    .day(courseTime.getDay())
                    .startTime(courseTime.getStartTime())
                    .endTime(courseTime.getEndTime()).build();
            courseTimeResponses.add(courseTimeResponse);
        }
        return courseTimeResponses;
    }

    public Long deleteById(Long courseId) {
        applicationCourseRepository.deleteById(courseId);
        return courseId;
    }
}
