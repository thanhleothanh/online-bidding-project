package com.ghtk.onlinebiddingproject.services;

import com.ghtk.onlinebiddingproject.models.dtos.DataMailDto;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendMail(DataMailDto dataMail, String templateName) throws MessagingException;
}
