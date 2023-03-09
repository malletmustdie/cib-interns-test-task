package ru.malletmustdie.cibinternstesttask.service;

import java.util.Locale;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.malletmustdie.cibinternstesttask.exception.ErrorType;

@Service
@RequiredArgsConstructor
public class MessageSourceHelper {

    private final MessageSource messageSource;

    public String getMessage(ErrorType errorType, Object... placeholders) {
        return messageSource.getMessage(
                errorType.name().toLowerCase(),
                placeholders,
                Locale.getDefault()
        );
    }
}
