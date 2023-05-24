package com.pop.planu.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Getter
public class IdNotFoundException extends RuntimeException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private final  int errorCode = 1;
    private final String errorMessage;
}
