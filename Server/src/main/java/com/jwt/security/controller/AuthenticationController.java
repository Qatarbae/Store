package com.jwt.security.controller;

import com.jwt.security.exception.CustomException;
import com.jwt.security.requestResponse.AuthenticationRequest;
import com.jwt.security.requestResponse.AuthenticationResponse;
import com.jwt.security.requestResponse.RegisterRequest;
import com.jwt.security.service.AuthenticationService;
import com.jwt.security.service.LogoutService;
import com.jwt.security.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final LogoutService logoutService;
    private final MailService mailService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        System.out.println("pp");
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request
    ) {

        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        try {
            service.refreshToken(request, response);
        } catch (RuntimeException ex) {
            // Обработка ошибки лимита запросов
            // Например, возвращаем ошибку с соответствующим HTTP-статусом
            throw new CustomException(HttpServletResponse.SC_BAD_REQUEST+ " Rate limit exceeded");
        } catch (Exception ex) {
            // Обработка других исключений, если необходимо
            // Например, возвращаем ошибку с соответствующим HTTP-статусом
            throw new CustomException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR + " Internal server error");
        }
    }

    @GetMapping("/up")
    public ResponseEntity up(
            @RequestParam("mail") String mail,
            @RequestParam("login") String login){

        String updatePasswordRequestId = service.initPasswordResetRequest(login);
        String ok;
        try {
            ok = mailService.getSenderText(mail,login, updatePasswordRequestId);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(ok);
    }
    @GetMapping("/updatePassword")
    public ResponseEntity updatePassword(
            @RequestParam("token") String token
    ){
        return ResponseEntity.ok(service.updatePassword(token));
    }
}
