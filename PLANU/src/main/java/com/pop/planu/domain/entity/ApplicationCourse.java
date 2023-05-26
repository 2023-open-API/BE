package com.pop.planu.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany(mappedBy = "applicationCourse", cascade = CascadeType.ALL)
    private List<CourseTime> courseTimeList;

    public void setCourseTimeList(List<CourseTime> courseTimeList) {
        this.courseTimeList = new ArrayList<>();
        for(CourseTime ct: courseTimeList) {
            ct.setApplicationCourse(this);
            this.courseTimeList.add(ct);
        }
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
