package com.ezticket.core.service;

import com.ezticket.core.pojo.EmailDetails;

public interface EmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);

    String sendCancelOrderMail(String name, String to, String porderno);
}
