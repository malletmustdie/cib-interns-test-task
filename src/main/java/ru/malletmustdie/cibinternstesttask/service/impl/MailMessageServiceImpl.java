package ru.malletmustdie.cibinternstesttask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.malletmustdie.cibinternstesttask.service.MailMessageService;

@Service
@RequiredArgsConstructor
public class MailMessageServiceImpl implements MailMessageService {

    @Value("${mail.email}")
    private String smtpName;

    @Override
    public SimpleMailMessage convertToMailMessage(String text) {
        var mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Error!");
        mailMessage.setText(text);
        mailMessage.setTo("mallet322@gmail.com", "meshkova.lina@gmail.com");
        mailMessage.setFrom(smtpName);
        return mailMessage;
    }

}
