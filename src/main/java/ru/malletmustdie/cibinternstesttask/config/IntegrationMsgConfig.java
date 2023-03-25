package ru.malletmustdie.cibinternstesttask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mail.MailHeaders;
import org.springframework.integration.mail.dsl.Mail;
import org.springframework.messaging.MessageChannel;
import ru.malletmustdie.cibinternstesttask.config.properties.MailProperties;

@Configuration
public class IntegrationMsgConfig {

    @Value("${mail.smtp.name}")
    private String smtpName;

    @Value("${mail.smtp.port}")
    private Integer port;

    @Value("${mail.smtp.auth}")
    private Boolean auth;

    @Value("${mail.smtp.starttlsenable}")
    private Boolean startTlsEnable;

    @Value("${mail.email}")
    private String email;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.protocol}")
    private String protocol;

    @Value("${mail.encoding}")
    private String encoding;

    @Value("${mail.debug}")
    private Boolean debug;

    @Bean("sendMailChannel")
    public MessageChannel sendMailChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow sendMailFlow() {
        return IntegrationFlows.from("sendMailChannel")
                               .handle(
                                       Mail.outboundAdapter(smtpName)
                                           .port(port)
                                           .protocol(protocol)
                                           .credentials(email, password)
                                           .defaultEncoding(encoding)
                                           .javaMailProperties(p -> {
                                               p.put("mail.debug", debug);
                                               p.put("mail.smtp.auth", auth);
                                               p.put("mail.smtp.starttls.enable", startTlsEnable);
                                           }),
                                       e -> e.id("sendMailEndpoint"))
                               .get();
    }

}
