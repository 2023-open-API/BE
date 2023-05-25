package com.pop.planu.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "applicationCourse")
public class ApplicationCourse {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicationCourseId")
    private Long id;

    @Column(name = "code", nullable = false)
    private Long code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "professor", nullable = false)
    private String professor;

    @OneToMany(mappedBy = "applicationCourse", cascade = CascadeType.ALL)
    private List<CourseTime> courseTimeList;

    public void setCourseTimeList(List<CourseTime> courseTimeList) {
        for(CourseTime ct: courseTimeList) {
            ct.setApplicationCourse(this);
            this.courseTimeList.add(ct);
        }
    }
}
