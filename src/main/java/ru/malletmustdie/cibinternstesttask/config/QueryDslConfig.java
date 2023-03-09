package ru.malletmustdie.cibinternstesttask.config;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация QueryDSL.
 */
@Configuration
public class QueryDslConfig {

    /**
     * Конфигурирует фабрику запроса.
     *
     * @param entityManager
     *         {@link EntityManager}.
     *
     * @return {@link JPAQueryFactory}.
     */
    @Bean
    public JPAQueryFactory queryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

}
