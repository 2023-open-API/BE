package com.pop.planu.domain.controller;

import com.pop.planu.domain.auth.AuthMember;
import com.pop.planu.domain.controller.request.todo.TodoRequest;
import com.pop.planu.domain.controller.response.todo.TodoResponse;
import com.pop.planu.domain.service.TodoService;
import com.pop.planu.domain.service.dto.todo.TodoDto;
import com.pop.planu.global.web.argumentresolver.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "투두", description = "투두 관련 api")
public class TodoRestController {
    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(Auth.class);
    }

    private final TodoService todoService;

    @PostMapping("/todo")
    @Operation(summary = "투두 저장", description = "새로운 투두를 저장합니다.")
    public ResponseEntity<Void> registerTodo(@Auth AuthMember authMember, @RequestBody TodoRequest todoRequest){
        Long todoId = todoService.save(todoRequest.toDto(authMember.getId()));

        URI uri = UriComponentsBuilder.fromPath("/todo/{todoId}")
                .buildAndExpand(todoId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/todo/{date}")
    @Operation(summary = "투두 목록 조회", description = "특정 날짜의 투두를 조회합니다.")
    public ResponseEntity<List<TodoResponse>> getTodoList(@Auth AuthMember authMember, @PathVariable LocalDate date) {
        List<TodoResponse> todoResponseList = todoService.getTodoListByDate(authMember.getId(), date);
        return ResponseEntity.ok(todoResponseList);
    }

    @GetMapping("/todo/{date}/{todoId}")
    @Operation(summary = "투두 정보 조회", description = "특정 날짜의 특정 투두 정보를 조회합니다.")
    public ResponseEntity<TodoResponse> getTodo(@Auth AuthMember authMember, @PathVariable LocalDate date, @PathVariable Long todoId) {
        TodoResponse todoResponse = todoService.getTodoById(todoId);
        return ResponseEntity.ok(todoResponse);
    }

    @PutMapping("/todo/{date}/{todoId}")
    @Operation(summary = "투두 수정", description = "특정 날짜의 특정 투두 정보를 수정합니다.")
    public ResponseEntity<TodoResponse> updateTodo(@Auth AuthMember authMember, @PathVariable Long todoId, @RequestBody TodoRequest todoRequest) {
        TodoResponse updatedResponse = todoService.update(todoRequest.toDto(authMember.getId(), todoId));
        return ResponseEntity.ok(updatedResponse);
    }

    @DeleteMapping("/todo/{date}/{todoId}")
    @Operation(summary = "투두 삭제", description = "특정 날짜의 특정 투두를 삭제합니다.")
    public ResponseEntity<Long> deleteTodo(@Auth AuthMember authMember, @PathVariable Long todoId) {
        Long deletedId = todoService.delete(todoId);
        return ResponseEntity.ok(deletedId);
    }
}
