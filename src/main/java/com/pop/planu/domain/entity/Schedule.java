package com.pop.planu.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private LocalDate startDate;

    @Column(name="endDate")
    private LocalDate endDate;

    @Column(name="color")
    private String color;

    public void update(String title, LocalDate startDate, LocalDate endDate, String color){
        this.title =title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.color = color;
    }

}
