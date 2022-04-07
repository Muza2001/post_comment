package com.example.springbootproject.servicies.Impl;

import com.example.springbootproject.dtos.request.NotificationEmail;
import com.example.springbootproject.servicies.MailSerivec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailSerivec {
    private final JavaMailSender javaMailSender;

    @Value("${general.transmitter.email}")
    String fromEmail;

    @Value("${general.transmitter.password}")
    String fromPassword;

    @Async
    public void send(NotificationEmail notificationEmail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setSubject(notificationEmail.getSubject());
        message.setTo(notificationEmail.getReceipt());
        message.setText(notificationEmail.getBody());
        try {
            log.info("Activation successfully");
            javaMailSender.send(message);
        }catch (MailException e) {
            log.error("Email not found" + e);
        }
    }

}
