package com.pop.planu.global.exception.handler;

import com.pop.planu.global.exception.StudentIdFoundException;
import com.pop.planu.global.exception.StudentIdDuplicatedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.pop.planu.global.exception.PasswordMismatchException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(StudentIdDuplicatedException.class)
    public ResponseEntity<ErrorMessage> studentIdDuplicatedException(StudentIdDuplicatedException e){
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(),e.getErrorCode(),e.getErrorMessage()));
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorMessage> passwordMismatchException(PasswordMismatchException e){
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(),e.getErrorCode(),e.getErrorMessage()));
    }

    @ExceptionHandler(StudentIdFoundException.class)
    public ResponseEntity<ErrorMessage> studentIdFoundException(StudentIdFoundException e){
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(),e.getErrorCode(),e.getErrorMessage()));
    }
}
