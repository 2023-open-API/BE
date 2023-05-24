package com.pop.planu.domain.service.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {
    private Long memberId;
    private Long todoId;
    private String title;
    private String subscription;
    private LocalDate date;
}
