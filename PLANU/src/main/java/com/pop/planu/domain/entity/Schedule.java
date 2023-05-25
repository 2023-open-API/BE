package com.pop.planu.domain.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="scheduleId")
    private long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name="title")
    private String title;

    @Column(name="startDate")
    private String startDate;

    @Column(name="endDate")
    private String endDate;

    @Column(name="color")
    private String color;

    public void update(String title, String startDate, String endDate, String color){
        this.title =title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.color = color;
    }

}
