package ru.malletmustdie.cibinternstesttask.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    INTERNAL_SERVER_ERROR("E001", "Internal server error"),

    BAD_REQUEST("400", "Bad request"),

    SOCK_NOT_FOUND("404", "Not found"),
    SOCK_NOT_FOUND_BY_CRITERIA("404", "Not found");

    private final String code;

    private final String message;

}
