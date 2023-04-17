package com.ezticket.core.controller;

import com.ezticket.core.pojo.EmailDetails;
import com.ezticket.core.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

//    @Autowired EmailDetails emailDetails;

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details) {
        String status = emailService.sendSimpleMail(details);
        System.out.print(status);
        return status;
    }

    @PostMapping("/test")
    String sendEmailtoMember() throws MessagingException {
//        emailDetails.setRecipient("si1993027@gmail.com");
//        emailDetails.setSubject("Test Subject");
//        emailDetails.setMsgBody("Test MessageBody");
//        emailService.sendSimpleMail("smile451012@gmail.com", "password");
        return "123";
    }

}
