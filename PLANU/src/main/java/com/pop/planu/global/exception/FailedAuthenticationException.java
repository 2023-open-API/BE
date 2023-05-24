package com.pop.planu.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class FailedAuthenticationException extends RuntimeException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private final int errorCode = 4;
    private final String errorMessage;
}
