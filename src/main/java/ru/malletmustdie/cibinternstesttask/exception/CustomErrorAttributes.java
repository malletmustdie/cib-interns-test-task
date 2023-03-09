package ru.malletmustdie.cibinternstesttask.exception;

import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomErrorAttributes extends DefaultErrorAttributes {

    private static final ErrorAttributeOptions OPTIONS = ErrorAttributeOptions.defaults();

    private final WebRequest webRequest;

    public Map<String, Object> getErrorAttributes(Exception exception, HttpStatus httpStatus) {
        var map = super.getErrorAttributes(webRequest, OPTIONS);
        var message = exception.getMessage();
        if (exception instanceof MethodArgumentNotValidException) {
            message = Optional.ofNullable(((MethodArgumentNotValidException) exception).getFieldError())
                              .map(DefaultMessageSourceResolvable::getDefaultMessage)
                              .orElse(exception.getMessage());
        }
        putAttributes(map, httpStatus, exception, message);
        return map;
    }

    private void putAttributes(Map<String, Object> map,
                               HttpStatus status,
                               Throwable error,
                               Object msg) {
        map.put("status", status.value());
        map.put("error", status.getReasonPhrase());
        map.put("exception", error.getClass().getCanonicalName());
        map.put("message", msg.toString());
    }

}

