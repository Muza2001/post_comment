package com.example.springbootproject.servicies;

import com.example.springbootproject.dtos.request.NotificationEmail;

public interface MailSerivec {
    void send(NotificationEmail notificationEmail);
}
