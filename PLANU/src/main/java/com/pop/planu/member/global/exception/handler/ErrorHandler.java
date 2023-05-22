package com.pop.planu.member.global.exception.handler;

import com.pop.planu.member.global.exception.StudentIdDuplicatedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.pop.planu.member.global.exception.StudentIdDuplicatedException;
import com.pop.planu.member.global.exception.PasswordMismatchException;
import com.pop.planu.member.global.exception.StudentIdFoundException;

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
