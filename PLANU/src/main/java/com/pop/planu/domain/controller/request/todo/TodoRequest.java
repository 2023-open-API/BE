package com.pop.planu.domain.controller.request.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pop.planu.domain.entity.Todo;
import com.pop.planu.domain.service.dto.todo.TodoDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequest {
    @NotBlank
    private String title;

    private String subscription;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    public TodoDto toDto(Long memberId) {
        return TodoDto.builder()
                .memberId(memberId)
                .title(this.title)
                .subscription(this.subscription)
                .date(this.date)
                .build();
    }

    public TodoDto toDto(Long memberId, Long todoId) {
        return TodoDto.builder()
                .memberId(memberId)
                .todoId(todoId)
                .title(this.title)
                .subscription(this.subscription)
                .date(this.date)
                .build();
    }
}
