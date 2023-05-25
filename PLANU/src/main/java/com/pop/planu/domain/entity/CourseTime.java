package com.pop.planu.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseTimeId")
    private Long id;

    @Column(name = "day")
    private String day;

    @Column(name = "startTime")
    private Long startTime;

    @Column(name= "endTime")
    private Long endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicationCourseId")
    private ApplicationCourse applicationCourse;

    public void setApplicationCourse(ApplicationCourse applicationCourse) {
        this.applicationCourse = applicationCourse;
    }
}
