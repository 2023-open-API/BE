package com.pop.planu.domain.service;

import com.pop.planu.domain.entity.Todo;
import com.pop.planu.domain.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;

    public Long save(String title, String subscription, LocalDate date) {
        Todo todo = Todo.builder()
                .title(title)
                .subscription(subscription)
                .date(date)
                .build();
        return todoRepository.save(todo).getId();
    }
}
