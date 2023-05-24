package com.pop.planu.domain.controller;

import com.pop.planu.domain.auth.AuthMember;
import com.pop.planu.domain.controller.request.todo.TodoRequest;
import com.pop.planu.domain.controller.response.todo.TodoResponse;
import com.pop.planu.domain.service.TodoService;
import com.pop.planu.domain.service.dto.todo.TodoDto;
import com.pop.planu.global.web.argumentresolver.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoRestController {
    private final TodoService todoService;

    @PostMapping("/todo")
    public ResponseEntity<Void> registerTodo(@Auth AuthMember authMember, @RequestBody TodoRequest todoRequest){
        Long todoId = todoService.save(todoRequest.toDto(authMember.getId()));

        URI uri = UriComponentsBuilder.fromPath("/todo/{todoId}")
                .buildAndExpand(todoId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/todo/{date}")
    public ResponseEntity<List<TodoResponse>> getTodoList(@Auth AuthMember authMember, @PathVariable LocalDate date) {
        List<TodoResponse> todoResponseList = todoService.getTodoListByDate(authMember.getId(), date);
        return ResponseEntity.ok(todoResponseList);
    }

    @GetMapping("/todo/{date}/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(@Auth AuthMember authMember, @PathVariable LocalDate date, @PathVariable Long todoId) {
        TodoResponse todoResponse = todoService.getTodoById(todoId);
        return ResponseEntity.ok(todoResponse);
    }

    @PutMapping("/todo/{date}/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(@Auth AuthMember authMember, @PathVariable Long todoId, @RequestBody TodoRequest todoRequest) {
        TodoResponse updatedResponse = todoService.update(todoRequest.toDto(authMember.getId(), todoId));
        return ResponseEntity.ok(updatedResponse);
    }

    @DeleteMapping("/todo/{date}/{todoId}")
    public ResponseEntity<Long> deleteTodo(@Auth AuthMember authMember, @PathVariable Long todoId) {
        Long deletedId = todoService.delete(todoId);
        return ResponseEntity.ok(deletedId);
    }
}
