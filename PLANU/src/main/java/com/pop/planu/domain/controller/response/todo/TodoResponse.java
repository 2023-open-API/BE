package com.pop.planu.domain.controller.response.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponse {
    private Long todoId;
    private String title;
    private String subscription;
    private String date;
}
