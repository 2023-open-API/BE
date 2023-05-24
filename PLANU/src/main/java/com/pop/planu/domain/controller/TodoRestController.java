package com.pop.planu.domain.controller;

import com.pop.planu.domain.controller.request.todo.TodoRequest;
import com.pop.planu.domain.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoRestController {
    private final TodoService todoService;

    @PostMapping("/todo")
    public ResponseEntity<Void> registerTodo(@RequestBody TodoRequest todoRequest){
        Long todoId = todoService.save(todoRequest.getTitle(), todoRequest.getSubscription(), todoRequest.getDate());

        URI uri = UriComponentsBuilder.fromPath("/todo/{todoId}")
                .buildAndExpand(todoId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }


}
