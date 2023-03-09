package ru.malletmustdie.cibinternstesttask.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "swagger-security")
@Configuration
public class SwaggerSecurityProperties {

    private String authorizationUrl;

    private String tokenUrl;

}
