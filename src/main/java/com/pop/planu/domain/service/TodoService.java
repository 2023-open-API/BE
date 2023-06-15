package com.pop.planu.domain.service;

import com.pop.planu.domain.controller.response.todo.TodoResponse;
import com.pop.planu.domain.entity.Member;
import com.pop.planu.domain.entity.Todo;
import com.pop.planu.domain.repository.MemberRepository;
import com.pop.planu.domain.repository.TodoRepository;
import com.pop.planu.domain.service.dto.todo.TodoDto;
import com.pop.planu.global.exception.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(TodoDto todoDto) {
        Member member = memberRepository.findById(todoDto.getMemberId())
                .orElseThrow(() -> new IdNotFoundException("회원 정보가 없습니다."));
        Todo todo = Todo.builder()
                .title(todoDto.getTitle())
                .subscription(todoDto.getSubscription())
                .date(todoDto.getDate())
                .member(member)
                .build();
        return todoRepository.save(todo).getId();
    }

    public List<TodoResponse> getTodoListByDate(Long memberId, LocalDate date) {
        List<Todo> todoList = todoRepository.findAllByMemberIdAndDate(memberId, date);
        List<TodoResponse> todoResponseList = new ArrayList<>();
        todoList.forEach(
                (todo) -> {
                    todoResponseList.add(
                            TodoResponse.builder()
                                    .todoId(todo.getId())
                                    .title(todo.getTitle())
                                    .subscription(todo.getSubscription())
                                    .date(todo.getDate().toString())
                                    .build());
                }
        );
        return todoResponseList;
    }

    public TodoResponse getTodoById(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IdNotFoundException("Todo 정보가 없습니다."));
        return TodoResponse.builder()
                .todoId(todo.getId())
                .title(todo.getTitle())
                .subscription(todo.getSubscription())
                .date(todo.getDate().toString()).build();
    }

    @Transactional
    public TodoResponse update(TodoDto todoDto) {
        Todo todo = todoRepository.findById(todoDto.getTodoId())
                .orElseThrow(() -> new IdNotFoundException("Todo 정보가 없습니다."));
        todo.update(todoDto.getTitle(), todoDto.getSubscription(), todoDto.getDate());
        return TodoResponse.builder()
                .todoId(todo.getId())
                .title(todo.getTitle())
                .subscription(todo.getSubscription())
                .date(todo.getDate().toString()).build();
    }

    public Long delete(Long todoId) {
        todoRepository.deleteById(todoId);
        return todoId;
    }
}
