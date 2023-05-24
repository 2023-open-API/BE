package com.pop.planu.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class StudentIdFoundException extends RuntimeException{
    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private final  int errorCode = 1;
    private final String errorMessage;
}
