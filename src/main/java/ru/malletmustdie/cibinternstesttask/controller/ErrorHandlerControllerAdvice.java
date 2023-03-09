package ru.malletmustdie.cibinternstesttask.controller;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.malletmustdie.cibinternstesttask.exception.BusinessException;
import ru.malletmustdie.cibinternstesttask.exception.CustomErrorAttributes;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ErrorHandlerControllerAdvice {

    private final CustomErrorAttributes customErrorAttributes;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var body = customErrorAttributes.getErrorAttributes(e, status);
        return ResponseEntity.status(status)
                             .body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(Exception e) {
        var status = HttpStatus.BAD_REQUEST;
        var body = customErrorAttributes.getErrorAttributes(e, status);
        return ResponseEntity.status(status)
                             .body(body);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(BusinessException e) {
        var errorType = e.getErrorType();
        switch (errorType) {
            case SOCK_NOT_FOUND:
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(customErrorAttributes.getErrorAttributes(e, HttpStatus.NOT_FOUND));
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body(customErrorAttributes.getErrorAttributes(
                                             e,
                                             HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

}
