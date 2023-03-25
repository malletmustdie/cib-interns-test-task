package ru.malletmustdie.cibinternstesttask.service;

import org.springframework.mail.SimpleMailMessage;

public interface MailMessageService {

    SimpleMailMessage convertToMailMessage(String text);

}
