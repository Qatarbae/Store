package com.jwt.security.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class MailService {


    private final JavaMailSender mailSender;
    private final JwtService jwtService;
    @Autowired
    private ResourceLoader resourceLoader;
    @Value("spring.mail.sender.email")
    private String senderEmail;
    @Value("spring.mail.sender.text")
    private String senderText;
    @Value("mail.link")
    private String mailLink;
    @Value("encoding")
    private String encoding;
    @Value("classpath")
    private String classpath;
    public MailService(JavaMailSender mailSender, JwtService jwtService) {
        this.mailSender = mailSender;
        this.jwtService = jwtService;
    }

    public String getSenderText(String receiver, String login, String updatePasswordRequestId) throws MessagingException, IOException {

        String jwtToken = jwtService.updatePasswordToken(login ,updatePasswordRequestId);
        String updateLink = mailLink+jwtToken;
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, encoding);

        // Указание адреса отправителя
        helper.setFrom(senderEmail);
        // Указание адреса получателя
        helper.setTo(receiver);
        // Указание темы письма
        helper.setSubject("Обновление пароля");

        // Загрузка HTML-шаблона из ресурсов
        Resource resource = resourceLoader.getResource(classpath);
        String htmlContent = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

        // Замена переменных в шаблоне на актуальные значения
        htmlContent = htmlContent.replace("${newPassword}", "PASSWORD");
        htmlContent = htmlContent.replace("${updateLink}", updateLink);

        // Указание HTML-контента письма
        helper.setText(htmlContent, true);

        // Отправка письма
        mailSender.send(message);

        return "ok";
    }
}