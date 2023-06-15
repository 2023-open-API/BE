package com.pop.planu.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseTimeId")
    private Long id;

    @Column(name = "dayOfTheWeek")
    private String day;

    @Column(name = "startTime")
    private LocalTime startTime;

    @Column(name= "endTime")
    private LocalTime endTime;

    @Column(name = "location")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicationCourseId")
    private ApplicationCourse applicationCourse;

    public void setApplicationCourse(ApplicationCourse applicationCourse) {
        this.applicationCourse = applicationCourse;
    }
}
