package ru.malletmustdie.cibinternstesttask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public abstract class AbstractDataFactoryNonContext {

    private final ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();

    private final JavaTimeModule javaTimeModule = new JavaTimeModule();

    /**
     * Генерирует тестовые данные их ресурсов.
     *
     * @param json
     * @param typeReference
     *         Ссылка на тип.
     * @param <T>
     *         Тип.
     *
     * @return Объект с тестовыми данными.
     */
    @SneakyThrows
    public <T> T getFromString(String json, TypeReference<T> typeReference) {
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper.readValue(json, typeReference);
    }

}
