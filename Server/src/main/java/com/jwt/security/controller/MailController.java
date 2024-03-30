package com.jwt.security.controller;

import com.jwt.security.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/mails")
public class MailController {

    private final MailService mail;

    public MailController(MailService mail) {
        this.mail = mail;
    }

//    @PostMapping("/send")
//    public String sendMail(@RequestParam("to") String to){
//        try {
//            return mail.getSenderText(to);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @GetMapping("/up")
    public ResponseEntity up(){
        return ResponseEntity.ok("Hello world");
    }
}