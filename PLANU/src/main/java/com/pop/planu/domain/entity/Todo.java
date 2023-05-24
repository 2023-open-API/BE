package com.pop.planu.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "todoId")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name="subscription")
    private String subscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
