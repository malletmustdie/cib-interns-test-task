package ru.malletmustdie.cibinternstesttask;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.malletmustdie.cibinternstesttask.dto.SockDto;

@Component
public class TestDataFactory extends AbstractTestDataFactory {

    private static final String SOCK_DTO_REFERENCE = "/data/json/sock-dto.json";

    @SneakyThrows
    public SockDto sockDto() {
        return getResource(SOCK_DTO_REFERENCE, new TypeReference<>() {
        });
    }

}
