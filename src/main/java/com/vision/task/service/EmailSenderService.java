package com.vision.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender mailSender;

    @Value("${mail.from}")
    private String fromMail;
    @Value("${mail.subject}")
    private String subjectMail;
    @Value("${mail.body}")
    private String bodyMail;

    @Async
    public void sendNotificationEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subjectMail);
        message.setText(bodyMail);
        message.setFrom(fromMail);

        mailSender.send(message);
    }
}
