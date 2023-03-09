package ru.malletmustdie.cibinternstesttask.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("ru.malletmustdie.cibinternstesttask.model")
@EnableJpaRepositories("ru.malletmustdie.cibinternstesttask.repository")
public class PersistentConfig {


}
