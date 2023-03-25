package ru.malletmustdie.cibinternstesttask.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "mail")
@Configuration
public class MailProperties {

    private String from;

    private String email;

    private String password;

    private String protocol;

    private String encoding;

    private Boolean debug;

    private Smtp smtp;

    @Getter
    @Setter
    public static class Smtp {

        private String name;

        private Integer port;

        private Boolean auth;

        private Boolean startTlsEnable;

    }

}
